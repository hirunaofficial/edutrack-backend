package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.entity.Enrollment;
import dev.hiruna.edutrack.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Enrollment getEnrollmentById(@PathVariable Integer id) {
        return enrollmentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @PutMapping("/{id}")
    public Enrollment updateEnrollment(@PathVariable Integer id, @RequestBody Enrollment enrollmentDetails) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElse(null);
        if (enrollment != null) {
            enrollment.setEnrollmentStatus(enrollmentDetails.getEnrollmentStatus());
            enrollment.setEnrollmentDate(enrollmentDetails.getEnrollmentDate());
            return enrollmentRepository.save(enrollment);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Integer id) {
        enrollmentRepository.deleteById(id);
    }
}
