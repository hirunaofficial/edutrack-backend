package dev.hiruna.edutrack.service;

import dev.hiruna.edutrack.dto.EnrollmentDTO;
import dev.hiruna.edutrack.entity.Course;
import dev.hiruna.edutrack.entity.Enrollment;
import dev.hiruna.edutrack.entity.User;
import dev.hiruna.edutrack.repository.CourseRepository;
import dev.hiruna.edutrack.repository.EnrollmentRepository;
import dev.hiruna.edutrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EnrollmentDTO getEnrollmentById(Integer id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElse(null);
        return enrollment != null ? convertToDTO(enrollment) : null;
    }

    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = convertToEntity(enrollmentDTO);
        enrollment = enrollmentRepository.save(enrollment);
        return convertToDTO(enrollment);
    }

    public EnrollmentDTO updateEnrollment(Integer id, EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElse(null);
        if (enrollment != null) {
            enrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
            enrollment.setEnrollmentStatus(enrollmentDTO.getEnrollmentStatus());

            User student = userRepository.findById(enrollmentDTO.getStudentId()).orElse(null);
            Course course = courseRepository.findById(enrollmentDTO.getCourseId()).orElse(null);

            if (student != null && course != null) {
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollment = enrollmentRepository.save(enrollment);
                return convertToDTO(enrollment);
            }
        }
        return null;
    }

    public void deleteEnrollment(Integer id) {
        enrollmentRepository.deleteById(id);
    }

    // Helper method to convert Enrollment entity to DTO
    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        return new EnrollmentDTO(enrollment.getId(), enrollment.getStudent().getId(),
                enrollment.getCourse().getId(), enrollment.getEnrollmentDate(), enrollment.getEnrollmentStatus());
    }

    // Helper method to convert DTO to Enrollment entity
    private Enrollment convertToEntity(EnrollmentDTO enrollmentDTO) {
        User student = userRepository.findById(enrollmentDTO.getStudentId()).orElse(null);
        Course course = courseRepository.findById(enrollmentDTO.getCourseId()).orElse(null);

        if (student != null && course != null) {
            return new Enrollment(student, course, enrollmentDTO.getEnrollmentDate(), enrollmentDTO.getEnrollmentStatus());
        }
        return null;
    }
}
