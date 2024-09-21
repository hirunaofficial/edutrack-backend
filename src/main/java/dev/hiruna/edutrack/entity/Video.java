package dev.hiruna.edutrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;

    private String videoTitle;
    private String videoUrl;

    @ManyToOne
    private Course course;

    public Video(String videoTitle, String videoUrl, Course course) {
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;
        this.course = course;
    }
}
