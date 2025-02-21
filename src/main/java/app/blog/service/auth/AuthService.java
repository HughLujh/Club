package app.blog.service.auth;

import app.blog.model.user.User;
import app.blog.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public Boolean isUserExist(String email){
        return userRepository.findByEmail(email)!=null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return (User) userRepository.findByEmail(email);
    }

    public Boolean isPasswordMatched(String password, String currentPassword){
        return passwordEncoder.matches(password, currentPassword);
    }
}
