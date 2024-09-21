package dev.hiruna.edutrack.repository;

import dev.hiruna.edutrack.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video,Integer> {
    void deleteByCourseId(Integer id);
}