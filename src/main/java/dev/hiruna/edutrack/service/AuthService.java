package dev.hiruna.edutrack.service;

import dev.hiruna.edutrack.dto.AuthResponseDTO;
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

    public AuthResponseDTO login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            String token = jwtAuthenticator.generateJwtToken(user);
            return new AuthResponseDTO("Bearer " + token, user.getUserType());
        }
        return null;
    }
}