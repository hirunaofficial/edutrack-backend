package dev.hiruna.edutrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Date enrollmentDate;
    private String enrollmentStatus;
}