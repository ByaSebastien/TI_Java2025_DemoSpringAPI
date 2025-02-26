package be.bstorm.demospringapi.api.models.comment.forms;

import be.bstorm.demospringapi.dl.entities.Comment;

public record CommentForm(
    String content
) {

    public Comment toComment() {
        return new Comment(this.content);
    }
}
