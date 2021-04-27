package com.project.cms.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseEnrollment {
    private String courseId;
    private String studentId;
    private boolean isAccepted;


}
