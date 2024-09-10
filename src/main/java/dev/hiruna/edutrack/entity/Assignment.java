package dev.hiruna.edutrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;

    private String title;
    private String description;
    private String courseId;
    private Date deadline;
    private String fileUrl;
    private Boolean isVersioned;
    private Integer totalMarks;
    private Boolean allowLateSubmissions;
    private Date lateSubmissionDeadline;

    @OneToMany(mappedBy = "assignment")
    private List<Submission> submissions;

    public Assignment(String title, String description, String courseId, Date deadline, String fileUrl, Boolean isVersioned, Integer totalMarks, Boolean allowLateSubmissions, Date lateSubmissionDeadline) {
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.deadline = deadline;
        this.fileUrl = fileUrl;
        this.isVersioned = isVersioned;
        this.totalMarks = totalMarks;
        this.allowLateSubmissions = allowLateSubmissions;
        this.lateSubmissionDeadline = lateSubmissionDeadline;
    }
}
