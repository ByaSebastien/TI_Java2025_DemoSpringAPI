package be.bstorm.demospringapi.bll.services.security.impls;

import be.bstorm.demospringapi.bll.exceptions.user.BadCredentialsException;
import be.bstorm.demospringapi.bll.exceptions.user.UserAlreadyExistException;
import be.bstorm.demospringapi.bll.exceptions.user.UserNotFoundException;
import be.bstorm.demospringapi.bll.services.security.AuthService;
import be.bstorm.demospringapi.dal.repositories.UserRepository;
import be.bstorm.demospringapi.dl.entities.User;
import be.bstorm.demospringapi.dl.enums.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void register(User user, MultipartFile image) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException(HttpStatus.BAD_REQUEST, "User with email " + user.getEmail() + " already exists");
        }

        if (!image.isEmpty()) {
            File uploadDir = new File("/uploads");
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = "/uploads/" + System.currentTimeMillis() + UUID.randomUUID() + image.getOriginalFilename();
            Path destination = Path.of(filePath);

            try {
                Files.copy(image.getInputStream(), destination);
                user.setImage(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User with email " + email + " not found")
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(HttpStatus.NOT_ACCEPTABLE, "Bad credentials");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND, email)
        );
    }
}
