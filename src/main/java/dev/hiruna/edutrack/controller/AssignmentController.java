package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.AssignmentDTO;
import dev.hiruna.edutrack.dto.ResponseDTO;
import dev.hiruna.edutrack.service.AssignmentService;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    // Helper method to check if the user's role is "Student"
    private boolean isStudent(String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            Map<String, Object> payload = jwtAuthenticator.getJwtPayload(authHeader);
            if (payload != null) {
                String role = (String) payload.get("role");
                return "Student".equalsIgnoreCase(role);
            }
        }
        return false;
    }

    // Helper method to check if the user's role is "Instructor" or "Admin"
    private boolean isInstructorOrAdmin(String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            Map<String, Object> payload = jwtAuthenticator.getJwtPayload(authHeader);
            if (payload != null) {
                String role = (String) payload.get("role");
                return "Instructor".equalsIgnoreCase(role) || "Admin".equalsIgnoreCase(role);
            }
        }
        return false;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<AssignmentDTO>>> getAllAssignments(@RequestHeader("Authorization") String authHeader) {
        if (isInstructorOrAdmin(authHeader)) {
            List<AssignmentDTO> assignments = assignmentService.getAllAssignments();
            ResponseDTO<List<AssignmentDTO>> response = new ResponseDTO<>("success", "Assignments fetched successfully", assignments);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<List<AssignmentDTO>> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<AssignmentDTO>> getAssignmentById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (isStudent(authHeader)) {
            AssignmentDTO assignmentDTO = assignmentService.getAssignmentById(id);
            ResponseDTO<AssignmentDTO> response = new ResponseDTO<>("success", "Assignment fetched successfully", assignmentDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<AssignmentDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<AssignmentDTO>> createAssignment(@RequestHeader("Authorization") String authHeader, @RequestBody AssignmentDTO assignmentDTO) {
        if (isInstructorOrAdmin(authHeader)) {
            AssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
            ResponseDTO<AssignmentDTO> response = new ResponseDTO<>("success", "Assignment created successfully", createdAssignment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        ResponseDTO<AssignmentDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<AssignmentDTO>> updateAssignment(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody AssignmentDTO assignmentDTO) {
        if (isInstructorOrAdmin(authHeader)) {
            AssignmentDTO updatedAssignment = assignmentService.updateAssignment(id, assignmentDTO);
            ResponseDTO<AssignmentDTO> response = new ResponseDTO<>("success", "Assignment updated successfully", updatedAssignment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<AssignmentDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteAssignment(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (isInstructorOrAdmin(authHeader)) {
            assignmentService.deleteAssignment(id);
            ResponseDTO<Void> response = new ResponseDTO<>("success", "Assignment deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        ResponseDTO<Void> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}