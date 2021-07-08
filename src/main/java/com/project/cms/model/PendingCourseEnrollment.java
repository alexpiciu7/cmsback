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
    private String studentEmail;

    public PendingCourseEnrollment(String id, String studentEmail) {
        this.id = id;
        this.studentEmail = studentEmail;
    }
}
