package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.AuthResponseDTO;
import dev.hiruna.edutrack.dto.LoginRequestDTO;
import dev.hiruna.edutrack.dto.ResponseDTO;
import dev.hiruna.edutrack.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<AuthResponseDTO>> login(@RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO authResponse = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (authResponse != null) {
            ResponseDTO<AuthResponseDTO> response = new ResponseDTO<>("success", "Login successful", authResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseDTO<AuthResponseDTO> response = new ResponseDTO<>("error", "Invalid credentials", null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}