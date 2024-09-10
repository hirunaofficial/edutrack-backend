package dev.hiruna.edutrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;

    @ManyToOne
    private Assignment assignment;

    private String studentId;

    private String submissionFileUrl;
    private Date submissionDate;
    private Boolean isLate;
    private Integer obtainedMarks;
    private String feedback;
    private Boolean isFinalVersion;

    public Submission(Assignment assignment, String studentId, String submissionFileUrl, Date submissionDate, Boolean isLate, Integer obtainedMarks, String feedback, Boolean isFinalVersion) {
        this.assignment = assignment;
        this.studentId = studentId;
        this.submissionFileUrl = submissionFileUrl;
        this.submissionDate = submissionDate;
        this.isLate = isLate;
        this.obtainedMarks = obtainedMarks;
        this.feedback = feedback;
        this.isFinalVersion = isFinalVersion;
    }
}
