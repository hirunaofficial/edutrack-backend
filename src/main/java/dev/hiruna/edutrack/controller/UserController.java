package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.UserDTO;
import dev.hiruna.edutrack.dto.ResponseDTO;
import dev.hiruna.edutrack.service.UserService;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            List<UserDTO> users = userService.getAllUsers();
            ResponseDTO<List<UserDTO>> response = new ResponseDTO<>("success", "Users fetched successfully", users);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<List<UserDTO>> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> getUserById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            UserDTO userDTO = userService.getUserById(id);
            ResponseDTO<UserDTO> response = new ResponseDTO<>("success", "User fetched successfully", userDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<UserDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<UserDTO>> createUser(@RequestHeader("Authorization") String authHeader, @RequestBody UserDTO userDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            UserDTO createdUser = userService.createUser(userDTO);
            ResponseDTO<UserDTO> response = new ResponseDTO<>("success", "User created successfully", createdUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        ResponseDTO<UserDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> updateUser(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody UserDTO userDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            ResponseDTO<UserDTO> response = new ResponseDTO<>("success", "User updated successfully", updatedUser);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<UserDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            userService.deleteUser(id);
            ResponseDTO<Void> response = new ResponseDTO<>("success", "User deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        ResponseDTO<Void> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}