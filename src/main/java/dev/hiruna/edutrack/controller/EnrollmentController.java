package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.EnrollmentDTO;
import dev.hiruna.edutrack.dto.ResponseDTO;
import dev.hiruna.edutrack.service.EnrollmentService;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    // Helper method to check if the user's role is "Admin"
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

    // Helper method to check if the user's role is either "Admin" or "Student"
    private boolean isAdminOrStudent(String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            Map<String, Object> payload = jwtAuthenticator.getJwtPayload(authHeader);
            if (payload != null) {
                String role = (String) payload.get("role");
                return "Admin".equalsIgnoreCase(role) || "Student".equalsIgnoreCase(role);
            }
        }
        return false;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<EnrollmentDTO>>> getAllEnrollments(@RequestHeader("Authorization") String authHeader) {
        if (isAdmin(authHeader)) {
            List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
            ResponseDTO<List<EnrollmentDTO>> response = new ResponseDTO<>("success", "Enrollments fetched successfully", enrollments);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<List<EnrollmentDTO>> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<EnrollmentDTO>> getEnrollmentById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (isAdminOrStudent(authHeader)) {
            EnrollmentDTO enrollmentDTO = enrollmentService.getEnrollmentById(id);
            ResponseDTO<EnrollmentDTO> response = new ResponseDTO<>("success", "Enrollment fetched successfully", enrollmentDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<EnrollmentDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<EnrollmentDTO>> createEnrollment(@RequestHeader("Authorization") String authHeader, @RequestBody EnrollmentDTO enrollmentDTO) {
        if (isAdmin(authHeader)) {
            EnrollmentDTO createdEnrollment = enrollmentService.createEnrollment(enrollmentDTO);
            ResponseDTO<EnrollmentDTO> response = new ResponseDTO<>("success", "Enrollment created successfully", createdEnrollment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        ResponseDTO<EnrollmentDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<EnrollmentDTO>> updateEnrollment(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody EnrollmentDTO enrollmentDTO) {
        if (isAdmin(authHeader)) {
            EnrollmentDTO updatedEnrollment = enrollmentService.updateEnrollment(id, enrollmentDTO);
            ResponseDTO<EnrollmentDTO> response = new ResponseDTO<>("success", "Enrollment updated successfully", updatedEnrollment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<EnrollmentDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteEnrollment(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (isAdmin(authHeader)) {
            enrollmentService.deleteEnrollment(id);
            ResponseDTO<Void> response = new ResponseDTO<>("success", "Enrollment deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        ResponseDTO<Void> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}