package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.SubmissionDTO;
import dev.hiruna.edutrack.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    public List<SubmissionDTO> getAllSubmissions() {
        return submissionService.getAllSubmissions();
    }

    @GetMapping("/{id}")
    public SubmissionDTO getSubmissionById(@PathVariable Integer id) {
        return submissionService.getSubmissionById(id);
    }

    @PostMapping
    public SubmissionDTO createSubmission(@RequestBody SubmissionDTO submissionDTO) {
        return submissionService.createSubmission(submissionDTO);
    }

    @PutMapping("/{id}")
    public SubmissionDTO updateSubmission(@PathVariable Integer id, @RequestBody SubmissionDTO submissionDTO) {
        return submissionService.updateSubmission(id, submissionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSubmission(@PathVariable Integer id) {
        submissionService.deleteSubmission(id);
    }
}