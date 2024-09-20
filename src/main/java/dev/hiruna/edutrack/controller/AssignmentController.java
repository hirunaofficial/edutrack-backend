package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.entity.Assignment;
import dev.hiruna.edutrack.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Assignment getAssignmentById(@PathVariable Integer id) {
        return assignmentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    @PutMapping("/{id}")
    public Assignment updateAssignment(@PathVariable Integer id, @RequestBody Assignment assignmentDetails) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if (assignment != null) {
            assignment.setTitle(assignmentDetails.getTitle());
            assignment.setDescription(assignmentDetails.getDescription());
            assignment.setDeadline(assignmentDetails.getDeadline());
            assignment.setFileUrl(assignmentDetails.getFileUrl());
            assignment.setIsVersioned(assignmentDetails.getIsVersioned());
            assignment.setTotalMarks(assignmentDetails.getTotalMarks());
            assignment.setAllowLateSubmissions(assignmentDetails.getAllowLateSubmissions());
            assignment.setLateSubmissionDeadline(assignmentDetails.getLateSubmissionDeadline());
            return assignmentRepository.save(assignment);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteAssignment(@PathVariable Integer id) {
        assignmentRepository.deleteById(id);
    }
}
