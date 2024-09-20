package dev.hiruna.edutrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private Integer id;
    private Integer assignmentId;
    private String studentId;
    private String submissionFileUrl;
    private Date submissionDate;
    private Boolean isLate;
    private Integer obtainedMarks;
    private String feedback;
    private Boolean isFinalVersion;
}