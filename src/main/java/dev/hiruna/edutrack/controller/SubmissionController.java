package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.entity.Submission;
import dev.hiruna.edutrack.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionRepository submissionRepository;

    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Submission getSubmissionById(@PathVariable Integer id) {
        return submissionRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Submission createSubmission(@RequestBody Submission submission) {
        return submissionRepository.save(submission);
    }

    @PutMapping("/{id}")
    public Submission updateSubmission(@PathVariable Integer id, @RequestBody Submission submissionDetails) {
        Submission submission = submissionRepository.findById(id).orElse(null);
        if (submission != null) {
            submission.setObtainedMarks(submissionDetails.getObtainedMarks());
            submission.setFeedback(submissionDetails.getFeedback());
            submission.setIsLate(submissionDetails.getIsLate());
            submission.setSubmissionDate(submissionDetails.getSubmissionDate());
            submission.setSubmissionFileUrl(submissionDetails.getSubmissionFileUrl());
            submission.setIsFinalVersion(submissionDetails.getIsFinalVersion());
            return submissionRepository.save(submission);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteSubmission(@PathVariable Integer id) {
        submissionRepository.deleteById(id);
    }
}
