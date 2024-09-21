package dev.hiruna.edutrack.service;

import dev.hiruna.edutrack.dto.CourseDTO;
import dev.hiruna.edutrack.dto.VideoDTO;
import dev.hiruna.edutrack.entity.Course;
import dev.hiruna.edutrack.entity.Video;
import dev.hiruna.edutrack.repository.CourseRepository;
import dev.hiruna.edutrack.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private VideoRepository videoRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Integer id) {
        Course course = courseRepository.findById(id).orElse(null);
        return course != null ? convertToDTO(course) : null;
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);

        course = courseRepository.save(course);

        if (courseDTO.getVideos() != null) {
            for (VideoDTO videoDTO : courseDTO.getVideos()) {
                Video video = convertToVideoEntity(videoDTO, course);
                videoRepository.save(video);
            }
        }

        return convertToDTO(course);
    }

    public CourseDTO updateCourse(Integer id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setTitle(courseDTO.getTitle());
            course.setDescription(courseDTO.getDescription());
            course.setInstructorId(courseDTO.getInstructorId());
            course.setCourseFileUrl(courseDTO.getCourseFileUrl());
            course.setCategory(courseDTO.getCategory());
            course.setDifficultyLevel(courseDTO.getDifficultyLevel());

            course = courseRepository.save(course);

            videoRepository.deleteByCourseId(course.getId());
            if (courseDTO.getVideos() != null) {
                for (VideoDTO videoDTO : courseDTO.getVideos()) {
                    Video video = convertToVideoEntity(videoDTO, course);
                    videoRepository.save(video);
                }
            }

            return convertToDTO(course);
        }
        return null;
    }

    public void deleteCourse(Integer id) {
        videoRepository.deleteByCourseId(id);
        courseRepository.deleteById(id);
    }

    // Helper method to convert Course entity to DTO
    private CourseDTO convertToDTO(Course course) {
        List<VideoDTO> videoDTOs = course.getVideos().stream().map(this::convertToVideoDTO).collect(Collectors.toList());
        return new CourseDTO(course.getId(), course.getTitle(), course.getDescription(),
                course.getInstructorId(), course.getCourseFileUrl(), course.getCategory(),
                course.getDifficultyLevel(), videoDTOs);
    }

    // Helper method to convert DTO to Course entity
    private Course convertToEntity(CourseDTO courseDTO) {
        return new Course(courseDTO.getTitle(), courseDTO.getDescription(),
                courseDTO.getInstructorId(), courseDTO.getCourseFileUrl(),
                courseDTO.getCategory(), courseDTO.getDifficultyLevel());
    }

    // Helper method to convert Video entity to VideoDTO
    private VideoDTO convertToVideoDTO(Video video) {
        return new VideoDTO(video.getId(), video.getVideoTitle(), video.getVideoUrl());
    }

    // Helper method to convert VideoDTO to Video entity
    private Video convertToVideoEntity(VideoDTO videoDTO, Course course) {
        return new Video(videoDTO.getVideoTitle(), videoDTO.getVideoUrl(), course);
    }
}