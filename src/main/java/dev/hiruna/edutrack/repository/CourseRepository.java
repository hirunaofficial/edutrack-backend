package dev.hiruna.edutrack.repository;

import dev.hiruna.edutrack.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {

}