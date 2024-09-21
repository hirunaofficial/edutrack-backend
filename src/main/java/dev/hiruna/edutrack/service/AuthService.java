package dev.hiruna.edutrack.service;

import dev.hiruna.edutrack.entity.User;
import dev.hiruna.edutrack.repository.UserRepository;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return jwtAuthenticator.generateJwtToken(user);
        }
        return null;
    }
}