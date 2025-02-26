package be.bstorm.demospringapi.api.controllers;

import be.bstorm.demospringapi.api.models.comment.forms.CommentForm;
import be.bstorm.demospringapi.bll.services.CommentService;
import be.bstorm.demospringapi.dl.entities.Comment;
import be.bstorm.demospringapi.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}")
    public ResponseEntity<Void> addComment(
            @PathVariable Long postId,
            @RequestBody CommentForm form,
            @AuthenticationPrincipal User user,
            UriComponentsBuilder uriBuilder
    ) {
        Comment comment = form.toComment();
        comment.setUser(user);
        Long id = commentService.addComment(postId, comment).getId();
        UriComponents uriComponents = uriBuilder.path("/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
}
