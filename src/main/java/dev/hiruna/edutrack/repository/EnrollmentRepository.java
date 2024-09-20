package dev.hiruna.edutrack.repository;

import dev.hiruna.edutrack.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Integer> {

}