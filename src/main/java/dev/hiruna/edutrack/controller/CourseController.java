package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.CourseDTO;
import dev.hiruna.edutrack.dto.ResponseDTO;
import dev.hiruna.edutrack.service.CourseService;
import dev.hiruna.edutrack.util.JWTAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<CourseDTO>>> getAllCourses(@RequestHeader("Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            List<CourseDTO> courses = courseService.getAllCourses();
            ResponseDTO<List<CourseDTO>> response = new ResponseDTO<>("success", "Courses fetched successfully", courses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<List<CourseDTO>> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CourseDTO>> getCourseById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDTO courseDTO = courseService.getCourseById(id);
            ResponseDTO<CourseDTO> response = new ResponseDTO<>("success", "Course fetched successfully", courseDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<CourseDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<CourseDTO>> createCourse(@RequestHeader("Authorization") String authHeader, @RequestBody CourseDTO courseDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDTO createdCourse = courseService.createCourse(courseDTO);
            ResponseDTO<CourseDTO> response = new ResponseDTO<>("success", "Course created successfully", createdCourse);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        ResponseDTO<CourseDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<CourseDTO>> updateCourse(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody CourseDTO courseDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
            ResponseDTO<CourseDTO> response = new ResponseDTO<>("success", "Course updated successfully", updatedCourse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseDTO<CourseDTO> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteCourse(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            courseService.deleteCourse(id);
            ResponseDTO<Void> response = new ResponseDTO<>("success", "Course deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        ResponseDTO<Void> response = new ResponseDTO<>("error", "Unauthorized access", null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}