package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.AuthResponseDTO;
import dev.hiruna.edutrack.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestParam String email, @RequestParam String password) {
        AuthResponseDTO authResponse = authService.login(email, password);
        if (authResponse != null) {
            return ResponseEntity.ok(authResponse);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}
