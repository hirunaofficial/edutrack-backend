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
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    // Helper method to check if the user has the "Admin" role
    private boolean isAdmin(String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            Map<String, Object> payload = jwtAuthenticator.getJwtPayload(authHeader);
            if (payload != null) {
                String role = (String) payload.get("role");
                return "Admin".equalsIgnoreCase(role);
            }
        }
        return false;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        if (isAdmin(authHeader)) {
            List<UserDTO> users = userService.getAllUsers();
            ResponseDTO<List<UserDTO>> response = new ResponseDTO<>("success", "Users fetched successfully", users);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<List<UserDTO>> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> getUserById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (isAdmin(authHeader)) {
            UserDTO userDTO = userService.getUserById(id);
            if (userDTO != null) {
                ResponseDTO<UserDTO> response = new ResponseDTO<>("success", "User fetched successfully", userDTO);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        ResponseDTO<UserDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<UserDTO>> createUser(@RequestHeader("Authorization") String authHeader, @RequestBody UserDTO userDTO) {
        if (isAdmin(authHeader)) {
            UserDTO createdUser = userService.createUser(userDTO);
            if (createdUser != null) {
                ResponseDTO<UserDTO> response = new ResponseDTO<>("success", "User created successfully", createdUser);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        ResponseDTO<UserDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> updateUser(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody UserDTO userDTO) {
        if (isAdmin(authHeader)) {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            if (updatedUser != null) {
                ResponseDTO<UserDTO> response = new ResponseDTO<>("success", "User updated successfully", updatedUser);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        ResponseDTO<UserDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteUser(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (isAdmin(authHeader)) {
            userService.deleteUser(id);
            ResponseDTO<Void> response = new ResponseDTO<>("success", "User deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        ResponseDTO<Void> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}