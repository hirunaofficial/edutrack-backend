package dev.hiruna.edutrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
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
}