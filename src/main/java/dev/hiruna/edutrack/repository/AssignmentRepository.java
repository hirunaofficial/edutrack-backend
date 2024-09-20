package dev.hiruna.edutrack.repository;

import dev.hiruna.edutrack.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {

}