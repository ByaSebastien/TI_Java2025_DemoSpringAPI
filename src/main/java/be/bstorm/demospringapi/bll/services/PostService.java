package be.bstorm.demospringapi.bll.services;

import be.bstorm.demospringapi.dl.entities.Post;
import be.bstorm.demospringapi.dl.entities.User;
import be.bstorm.demospringapi.il.utils.request.SearchParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    Post save(Post post);
    Page<Post> getPosts(List<SearchParam<Post>> searchParams, Pageable pageable);
}
