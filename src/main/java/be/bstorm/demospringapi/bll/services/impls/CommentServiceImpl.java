package be.bstorm.demospringapi.bll.services.impls;

import be.bstorm.demospringapi.bll.exceptions.post.PostNotFoundException;
import be.bstorm.demospringapi.bll.services.CommentService;
import be.bstorm.demospringapi.dal.repositories.CommentRepository;
import be.bstorm.demospringapi.dal.repositories.PostRepository;
import be.bstorm.demospringapi.dl.entities.Comment;
import be.bstorm.demospringapi.dl.entities.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public Comment addComment(Long postId, Comment comment) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException(HttpStatus.NOT_FOUND, "Post with id " + postId + " not found")
        );
        comment.setPost(post);

        return commentRepository.save(comment);
    }
}
