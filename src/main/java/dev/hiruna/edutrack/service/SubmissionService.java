package dev.hiruna.edutrack.service;

import dev.hiruna.edutrack.dto.SubmissionDTO;
import dev.hiruna.edutrack.entity.Assignment;
import dev.hiruna.edutrack.entity.Submission;
import dev.hiruna.edutrack.repository.AssignmentRepository;
import dev.hiruna.edutrack.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<SubmissionDTO> getAllSubmissions() {
        return submissionRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public SubmissionDTO getSubmissionById(Integer id) {
        Submission submission = submissionRepository.findById(id).orElse(null);
        return submission != null ? convertToDTO(submission) : null;
    }

    public SubmissionDTO createSubmission(SubmissionDTO submissionDTO) {
        Submission submission = convertToEntity(submissionDTO);
        submission = submissionRepository.save(submission);
        return convertToDTO(submission);
    }

    public SubmissionDTO updateSubmission(Integer id, SubmissionDTO submissionDTO) {
        Submission submission = submissionRepository.findById(id).orElse(null);
        if (submission != null) {
            submission.setSubmissionFileUrl(submissionDTO.getSubmissionFileUrl());
            submission.setSubmissionDate(submissionDTO.getSubmissionDate());
            submission.setIsLate(submissionDTO.getIsLate());
            submission.setObtainedMarks(submissionDTO.getObtainedMarks());
            submission.setFeedback(submissionDTO.getFeedback());
            submission.setIsFinalVersion(submissionDTO.getIsFinalVersion());

            Assignment assignment = assignmentRepository.findById(submissionDTO.getAssignmentId()).orElse(null);
            if (assignment != null) {
                submission.setAssignment(assignment);
                submission = submissionRepository.save(submission);
                return convertToDTO(submission);
            }
        }
        return null;
    }

    public void deleteSubmission(Integer id) {
        submissionRepository.deleteById(id);
    }

    // Helper method to convert Submission entity to DTO
    private SubmissionDTO convertToDTO(Submission submission) {
        return new SubmissionDTO(submission.getId(), submission.getAssignment().getId(),
                submission.getStudentId(), submission.getSubmissionFileUrl(), submission.getSubmissionDate(),
                submission.getIsLate(), submission.getObtainedMarks(), submission.getFeedback(), submission.getIsFinalVersion());
    }

    // Helper method to convert DTO to Submission entity
    private Submission convertToEntity(SubmissionDTO submissionDTO) {
        Assignment assignment = assignmentRepository.findById(submissionDTO.getAssignmentId()).orElse(null);
        if (assignment != null) {
            return new Submission(assignment, submissionDTO.getStudentId(), submissionDTO.getSubmissionFileUrl(),
                    submissionDTO.getSubmissionDate(), submissionDTO.getIsLate(), submissionDTO.getObtainedMarks(),
                    submissionDTO.getFeedback(), submissionDTO.getIsFinalVersion());
        }
        return null;
    }
}