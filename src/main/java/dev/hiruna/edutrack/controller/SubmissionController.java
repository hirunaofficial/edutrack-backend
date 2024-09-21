package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.SubmissionDTO;
import dev.hiruna.edutrack.service.SubmissionService;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    @GetMapping
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions(@RequestHeader("Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            return new ResponseEntity<>(submissionService.getAllSubmissions(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            SubmissionDTO submissionDTO = submissionService.getSubmissionById(id);
            return new ResponseEntity<>(submissionDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<SubmissionDTO> createSubmission(@RequestHeader("Authorization") String authHeader, @RequestBody SubmissionDTO submissionDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            SubmissionDTO createdSubmission = submissionService.createSubmission(submissionDTO);
            return new ResponseEntity<>(createdSubmission, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubmissionDTO> updateSubmission(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody SubmissionDTO submissionDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            SubmissionDTO updatedSubmission = submissionService.updateSubmission(id, submissionDTO);
            return new ResponseEntity<>(updatedSubmission, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            submissionService.deleteSubmission(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}