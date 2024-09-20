package dev.hiruna.edutrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Integer id;
    private String title;
    private String description;
    private String instructorId;
    private String courseFileUrl;
    private String category;
    private String difficultyLevel;
}