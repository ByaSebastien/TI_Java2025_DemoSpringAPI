package be.bstorm.demospringapi.api.models.comment.dtos;

import be.bstorm.demospringapi.api.models.user.dtos.UserDTO;
import be.bstorm.demospringapi.dl.entities.Comment;

public record CommentDTO(
        Long id,
        String content,
        UserDTO user
) {

    public static CommentDTO fromComment(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getContent(), UserDTO.fromUser(comment.getUser()));
    }
}
