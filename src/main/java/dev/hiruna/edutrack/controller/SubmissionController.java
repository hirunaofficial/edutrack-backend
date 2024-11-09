package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.SubmissionDTO;
import dev.hiruna.edutrack.dto.ResponseDTO;
import dev.hiruna.edutrack.service.SubmissionService;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    // Helper method to check if the user's role is "Student" or "Instructor"
    private boolean isAuthorizedRole(String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            Map<String, Object> payload = jwtAuthenticator.getJwtPayload(authHeader);
            if (payload != null) {
                String role = (String) payload.get("role");
                return "Student".equalsIgnoreCase(role) || "Instructor".equalsIgnoreCase(role);
            }
        }
        return false;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<SubmissionDTO>>> getAllSubmissions(@RequestHeader("Authorization") String authHeader) {
        if (isAuthorizedRole(authHeader)) {
            List<SubmissionDTO> submissions = submissionService.getAllSubmissions();
            ResponseDTO<List<SubmissionDTO>> response = new ResponseDTO<>("success", "Submissions fetched successfully", submissions);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<List<SubmissionDTO>> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmissionDTO>> getSubmissionById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (isAuthorizedRole(authHeader)) {
            SubmissionDTO submissionDTO = submissionService.getSubmissionById(id);
            ResponseDTO<SubmissionDTO> response = new ResponseDTO<>("success", "Submission fetched successfully", submissionDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<SubmissionDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmissionDTO>> createSubmission(@RequestHeader("Authorization") String authHeader, @RequestBody SubmissionDTO submissionDTO) {
        if (isAuthorizedRole(authHeader)) {
            SubmissionDTO createdSubmission = submissionService.createSubmission(submissionDTO);
            ResponseDTO<SubmissionDTO> response = new ResponseDTO<>("success", "Submission created successfully", createdSubmission);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        ResponseDTO<SubmissionDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmissionDTO>> updateSubmission(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody SubmissionDTO submissionDTO) {
        if (isAuthorizedRole(authHeader)) {
            SubmissionDTO updatedSubmission = submissionService.updateSubmission(id, submissionDTO);
            ResponseDTO<SubmissionDTO> response = new ResponseDTO<>("success", "Submission updated successfully", updatedSubmission);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<SubmissionDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteSubmission(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (isAuthorizedRole(authHeader)) {
            submissionService.deleteSubmission(id);
            ResponseDTO<Void> response = new ResponseDTO<>("success", "Submission deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        ResponseDTO<Void> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}