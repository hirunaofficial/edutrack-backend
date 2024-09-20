package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.AssignmentDTO;
import dev.hiruna.edutrack.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    public List<AssignmentDTO> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{id}")
    public AssignmentDTO getAssignmentById(@PathVariable Integer id) {
        return assignmentService.getAssignmentById(id);
    }

    @PostMapping
    public AssignmentDTO createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        return assignmentService.createAssignment(assignmentDTO);
    }

    @PutMapping("/{id}")
    public AssignmentDTO updateAssignment(@PathVariable Integer id, @RequestBody AssignmentDTO assignmentDTO) {
        return assignmentService.updateAssignment(id, assignmentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAssignment(@PathVariable Integer id) {
        assignmentService.deleteAssignment(id);
    }
}