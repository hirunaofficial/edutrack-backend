package dev.hiruna.edutrack.repository;

import dev.hiruna.edutrack.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission,Integer> {

}