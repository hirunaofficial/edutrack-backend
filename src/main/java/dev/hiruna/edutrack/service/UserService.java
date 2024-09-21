package dev.hiruna.edutrack.service;

import dev.hiruna.edutrack.dto.UserDTO;
import dev.hiruna.edutrack.entity.User;
import dev.hiruna.edutrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred while hashing password", e);
        }
    }

    // Helper method to convert byte array to hex string
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? convertToDTO(user) : null;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user.setPassword(hashPassword(user.getPassword()));
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEmail(userDTO.getEmail());
            user.setPassword(hashPassword(userDTO.getPassword()));
            user.setRole(userDTO.getRole());
            user = userRepository.save(user);
            return convertToDTO(user);
        }
        return null;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // Helper method to convert User entity to DTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getPassword(), user.getRole());
    }

    // Helper method to convert DTO to User entity
    private User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getRole());
    }
}