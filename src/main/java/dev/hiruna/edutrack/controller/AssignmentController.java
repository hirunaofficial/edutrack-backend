package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.AssignmentDTO;
import dev.hiruna.edutrack.service.AssignmentService;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments(@RequestHeader("Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            return new ResponseEntity<>(assignmentService.getAllAssignments(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            AssignmentDTO assignmentDTO = assignmentService.getAssignmentById(id);
            return new ResponseEntity<>(assignmentDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestHeader("Authorization") String authHeader, @RequestBody AssignmentDTO assignmentDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            AssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
            return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody AssignmentDTO assignmentDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            AssignmentDTO updatedAssignment = assignmentService.updateAssignment(id, assignmentDTO);
            return new ResponseEntity<>(updatedAssignment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            assignmentService.deleteAssignment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}