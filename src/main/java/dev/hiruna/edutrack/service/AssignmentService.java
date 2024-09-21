package dev.hiruna.edutrack.service;

import dev.hiruna.edutrack.dto.AssignmentDTO;
import dev.hiruna.edutrack.entity.Assignment;
import dev.hiruna.edutrack.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<AssignmentDTO> getAllAssignments() {
        return assignmentRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AssignmentDTO getAssignmentById(Integer id) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        return assignment != null ? convertToDTO(assignment) : null;
    }

    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = convertToEntity(assignmentDTO);
        assignment = assignmentRepository.save(assignment);
        return convertToDTO(assignment);
    }

    public AssignmentDTO updateAssignment(Integer id, AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);
        if (assignment != null) {
            assignment.setTitle(assignmentDTO.getTitle());
            assignment.setDescription(assignmentDTO.getDescription());
            assignment.setDeadline(assignmentDTO.getDeadline());
            assignment.setFileUrl(assignmentDTO.getFileUrl());
            assignment.setIsVersioned(assignmentDTO.getIsVersioned());
            assignment.setTotalMarks(assignmentDTO.getTotalMarks());
            assignment.setAllowLateSubmissions(assignmentDTO.getAllowLateSubmissions());
            assignment.setLateSubmissionDeadline(assignmentDTO.getLateSubmissionDeadline());
            assignment = assignmentRepository.save(assignment);
            return convertToDTO(assignment);
        }
        return null;
    }

    public void deleteAssignment(Integer id) {
        assignmentRepository.deleteById(id);
    }

    // Helper method to convert Assignment entity to DTO
    private AssignmentDTO convertToDTO(Assignment assignment) {
        return new AssignmentDTO(assignment.getId(), assignment.getTitle(), assignment.getDescription(),
                assignment.getCourseId(), assignment.getDeadline(), assignment.getFileUrl(),
                assignment.getIsVersioned(), assignment.getTotalMarks(), assignment.getAllowLateSubmissions(),
                assignment.getLateSubmissionDeadline());
    }

    // Helper method to convert DTO to Assignment entity
    private Assignment convertToEntity(AssignmentDTO assignmentDTO) {
        return new Assignment(assignmentDTO.getTitle(), assignmentDTO.getDescription(),
                assignmentDTO.getCourseId(), assignmentDTO.getDeadline(), assignmentDTO.getFileUrl(),
                assignmentDTO.getIsVersioned(), assignmentDTO.getTotalMarks(),
                assignmentDTO.getAllowLateSubmissions(), assignmentDTO.getLateSubmissionDeadline());
    }
}