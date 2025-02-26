package be.bstorm.demospringapi.bll.services.security;

import be.bstorm.demospringapi.dl.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService extends UserDetailsService {

    void register(User user, MultipartFile image);
    User login(String email, String password);
}
