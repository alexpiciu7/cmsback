package com.project.cms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingCourseEnrollment {
    @Id
    private String id;
    private String courseId;
    private String courseName;
    private String studentEmail;
    private String studentName;

    public PendingCourseEnrollment(String courseId, String courseName, String studentEmail, String studentName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentEmail = studentEmail;
        this.studentName = studentName;
    }
}
