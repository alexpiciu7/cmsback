package com.project.cms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PendingCourseEnrollment {
    private String courseId;
    private String studentEmail;

}
