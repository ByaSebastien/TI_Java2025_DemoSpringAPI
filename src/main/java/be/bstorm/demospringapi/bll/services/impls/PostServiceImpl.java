package be.bstorm.demospringapi.bll.services.impls;

import be.bstorm.demospringapi.bll.services.PostService;
import be.bstorm.demospringapi.bll.specifications.PostSpecification;
import be.bstorm.demospringapi.dal.repositories.CommentRepository;
import be.bstorm.demospringapi.dal.repositories.PostRepository;
import be.bstorm.demospringapi.dl.entities.Comment;
import be.bstorm.demospringapi.dl.entities.Post;
import be.bstorm.demospringapi.il.utils.request.SearchParam;
import be.bstorm.demospringapi.il.utils.specifications.SearchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public Post save(Post post) {
        //Todo Handle image
        return postRepository.save(post);
    }

    @Cacheable(value = "posts", key = "#searchParams.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    @Override
    public Page<Post> getPosts(List<SearchParam<Post>> searchParams, Pageable pageable) {
        System.out.println("❌ Cache MISS - Requête exécutée !");
        Specification<Post> joinSpec = Specification.allOf(
                PostSpecification.joinComments(),
                PostSpecification.joinOwner()
//                PostSpecification.joinLikers()
        );
        Page<Post> posts;
        if (searchParams.isEmpty()) {
            posts = postRepository.findAll(joinSpec, pageable);
        } else {
            posts = postRepository.findAll(
                    Specification.allOf(
                            searchParams.stream()
                                    .map(SearchSpecification::search)
                                    .toList()
                    ).and(joinSpec),
                    pageable
            );
        }

        List<Long> postIds = posts.stream().map(Post::getId).toList();

        List<Comment> comments = commentRepository.findLastThreeCommentsForPosts(postIds);

        Map<Long, List<Comment>> commentsByPost = comments.stream()
                .collect(Collectors.groupingBy(c -> c.getPost().getId()));

        return posts.map(post -> {
            List<Comment> postComments = commentsByPost.getOrDefault(post.getId(), List.of());
            post.setComments(postComments);
            return post;
        });
    }
}
