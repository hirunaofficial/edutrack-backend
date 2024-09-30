package dev.hiruna.edutrack.service;

import dev.hiruna.edutrack.dto.AuthResponseDTO;
import dev.hiruna.edutrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public AuthResponseDTO login(String email, String password) {
        return userService.login(email, password);
    }
}
