package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.EnrollmentDTO;
import dev.hiruna.edutrack.service.EnrollmentService;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments(@RequestHeader("Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            return new ResponseEntity<>(enrollmentService.getAllEnrollments(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            EnrollmentDTO enrollmentDTO = enrollmentService.getEnrollmentById(id);
            return new ResponseEntity<>(enrollmentDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestHeader("Authorization") String authHeader, @RequestBody EnrollmentDTO enrollmentDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            EnrollmentDTO createdEnrollment = enrollmentService.createEnrollment(enrollmentDTO);
            return new ResponseEntity<>(createdEnrollment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody EnrollmentDTO enrollmentDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            EnrollmentDTO updatedEnrollment = enrollmentService.updateEnrollment(id, enrollmentDTO);
            return new ResponseEntity<>(updatedEnrollment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            enrollmentService.deleteEnrollment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}