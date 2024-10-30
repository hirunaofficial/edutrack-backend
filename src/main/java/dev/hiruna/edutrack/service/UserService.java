package dev.hiruna.edutrack.service;

import dev.hiruna.edutrack.dto.AuthResponseDTO;
import dev.hiruna.edutrack.dto.UserDTO;
import dev.hiruna.edutrack.entity.User;
import dev.hiruna.edutrack.repository.UserRepository;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    // Use BCryptPasswordEncoder for hashing and checking passwords
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Hash password using BCrypt
        user = userRepository.save(user);
        return convertToDTOWithoutPassword(user); // Do not return password in response
    }

    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Hash the new password
            user.setRole(userDTO.getRole());
            user = userRepository.save(user);
            return convertToDTOWithoutPassword(user); // Do not return password in response
        }
        return null;
    }

    public AuthResponseDTO login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) { // Check hashed password
            String token = jwtAuthenticator.generateJwtToken(user); // Generate JWT token for login
            return new AuthResponseDTO("Bearer " + token, user.getUserType());
        }
        return null;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTOWithoutPassword).collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? convertToDTOWithoutPassword(user) : null;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // Helper method to convert User entity to DTO, excluding the password
    private UserDTO convertToDTOWithoutPassword(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getPhoneNumber(), null, user.getRole());
    }

    // Helper method to convert DTO to User entity
    private User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.getEmail(), userDTO.getPhoneNumber(), userDTO.getPassword(), userDTO.getRole());
    }
}