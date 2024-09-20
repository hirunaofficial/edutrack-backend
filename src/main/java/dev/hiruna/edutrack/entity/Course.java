package dev.hiruna.edutrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;

    private String title;
    private String description;
    private String instructorId;
    private String courseFileUrl;
    private String category;
    private String difficultyLevel;

    public Course(String title, String description, String instructorId, String courseFileUrl, String category, String difficultyLevel) {
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.courseFileUrl = courseFileUrl;
        this.category = category;
        this.difficultyLevel = difficultyLevel;
    }
}