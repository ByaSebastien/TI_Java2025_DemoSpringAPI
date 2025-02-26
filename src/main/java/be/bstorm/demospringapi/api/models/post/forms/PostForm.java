package be.bstorm.demospringapi.api.models.post.forms;

import be.bstorm.demospringapi.dl.entities.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostForm(
        @NotBlank @Size(max = 100)
        String title,
        @NotBlank @Size(max = 255)
        String content
) {

    public Post toPost() {
        return new Post(this.title, this.content);
    }
}
