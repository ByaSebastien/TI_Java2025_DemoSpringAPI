package be.bstorm.demospringapi.api.controllers;

import be.bstorm.demospringapi.api.models.CustomPage;
import be.bstorm.demospringapi.api.models.post.dtos.PostDTO;
import be.bstorm.demospringapi.api.models.post.forms.PostForm;
import be.bstorm.demospringapi.bll.services.PostService;
import be.bstorm.demospringapi.dl.entities.Post;
import be.bstorm.demospringapi.dl.entities.User;
import be.bstorm.demospringapi.il.utils.request.SearchParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<CustomPage<PostDTO>> getPosts(
            @RequestParam(required = false) Map<String, String> params,
            @RequestParam (required = false,defaultValue = "1") int page,
            @RequestParam (required = false,defaultValue = "10") int size,
            @RequestParam (required = false,defaultValue = "title") String sort
    ) {
        List<SearchParam<Post>> searchParams = SearchParam.create(params);
        Page<Post> posts = postService.getPosts(
                searchParams,
                PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, sort))
                );
        List<PostDTO> dtos = posts.getContent().stream()
                .map(PostDTO::fromPost)
                .toList();
        CustomPage<PostDTO> result = new CustomPage<>(dtos,posts.getTotalPages(),posts.getNumber() + 1);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Void> createPost(
            @Valid @RequestBody PostForm form,
            @AuthenticationPrincipal User user
    ) {
        //Todo Gestion d'exception cleaner BindingResult
        Post post = form.toPost();
        post.setOwner(user);
        Long id = postService.save(post).getId();
        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
}
