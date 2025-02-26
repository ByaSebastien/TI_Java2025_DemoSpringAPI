package be.bstorm.demospringapi.bll.services.security.impls;

import be.bstorm.demospringapi.bll.services.security.AuthService;
import be.bstorm.demospringapi.dal.repositories.UserRepository;
import be.bstorm.demospringapi.dl.entities.User;
import be.bstorm.demospringapi.dl.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            //Todo Gestion d'exception cleaner
            throw new UsernameNotFoundException("User with email " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        //Todo Handle image
        userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> {
                    //Todo Gestion d'exception cleaner
                    throw new UsernameNotFoundException("User with email " + email + " not found");
                }
        );
        if(!passwordEncoder.matches(password, user.getPassword())) {
            //Todo Gestion d'exception cleaner
            throw new BadCredentialsException("Bad credentials");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email)
        );
    }
}
