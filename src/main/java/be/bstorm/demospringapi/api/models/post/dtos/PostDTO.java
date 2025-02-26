package be.bstorm.demospringapi.api.models.post.dtos;

import be.bstorm.demospringapi.api.models.comment.dtos.CommentDTO;
import be.bstorm.demospringapi.api.models.user.dtos.UserDTO;
import be.bstorm.demospringapi.dl.entities.Post;

import java.util.List;

public record PostDTO(
        Long id,
        String title,
        String content,
        String image,
        UserDTO user,
        List<CommentDTO> comments
) {

    public static PostDTO fromPost(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImage(),
                UserDTO.fromUser(post.getOwner()),
                post.getComments().stream().map(CommentDTO::fromComment).toList()
        );
    }
}
