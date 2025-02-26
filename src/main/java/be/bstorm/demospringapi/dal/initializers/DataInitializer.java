package be.bstorm.demospringapi.dal.initializers;

import be.bstorm.demospringapi.dal.repositories.CommentRepository;
import be.bstorm.demospringapi.dal.repositories.PostRepository;
import be.bstorm.demospringapi.dal.repositories.UserRepository;
import be.bstorm.demospringapi.dl.entities.Comment;
import be.bstorm.demospringapi.dl.entities.Post;
import be.bstorm.demospringapi.dl.entities.User;
import be.bstorm.demospringapi.dl.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {

            String password = passwordEncoder.encode("Test1234=");

            List<User> users = List.of(
                    new User(
                            "admin@admin.be",
                            password,
                            "admin",
                            "admin",
                            LocalDate.now(),
                            UserRole.ADMIN,
                            null),
                    new User(
                            "user@user.be",
                            password,
                            "user",
                            "user",
                            LocalDate.now(),
                            UserRole.USER,
                            null),
                    new User(
                            "modo@modo.be",
                            password,
                            "modo",
                            "modo",
                            LocalDate.now(),
                            UserRole.MODERATOR,
                            null)
                    );
            userRepository.saveAll(users);
        }

        if(postRepository.count() == 0) {
            List<Post> posts = List.of(
                    new Post(
                            "Titre 1",
                            "Content 1",
                            "Image 1",
                            userRepository.findByEmail("admin@admin.be").orElseThrow()
                    ),
                    new Post(
                            "Titre 2",
                            "Content 2",
                            "Image 2",
                            userRepository.findByEmail("user@user.be").orElseThrow()
                    ),
                    new Post(
                            "Titre 3",
                            "Content 3",
                            "Image 3",
                            userRepository.findByEmail("modo@modo.be").orElseThrow()
                    )
            );
            postRepository.saveAll(posts);
        }

        if(commentRepository.count() == 0) {
            List<Comment> comments = List.of(
                    new Comment(
                            "comment 1",
                            postRepository.findById(1L).orElseThrow(),
                            userRepository.findByEmail("admin@admin.be").orElseThrow()
                    ),
                    new Comment(
                            "comment 2",
                            postRepository.findById(1L).orElseThrow(),
                            userRepository.findByEmail("modo@modo.be").orElseThrow()
                    ),
                    new Comment(
                            "comment 3",
                            postRepository.findById(2L).orElseThrow(),
                            userRepository.findByEmail("user@user.be").orElseThrow()
                    )
            );
            commentRepository.saveAll(comments);
        }
    }
}
