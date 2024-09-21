package dev.hiruna.edutrack.controller;

import dev.hiruna.edutrack.dto.CourseDTO;
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
    public ResponseEntity<List<CourseDTO>> getAllCourses(@RequestHeader("Authorization") String authHeader) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDTO courseDTO = courseService.getCourseById(id);
            return new ResponseEntity<>(courseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestHeader("Authorization") String authHeader, @RequestBody CourseDTO courseDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDTO createdCourse = courseService.createCourse(courseDTO);
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id, @RequestBody CourseDTO courseDTO) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@RequestHeader("Authorization") String authHeader, @PathVariable Integer id) {
        if (jwtAuthenticator.validateJwtToken(authHeader)) {
            courseService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}