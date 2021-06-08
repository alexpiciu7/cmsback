package com.project.cms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PendingGroupEnrollment {
    @Id
    private String id;
    private String courseId;
    private String groupNo;
    private String studentEmail;

    public PendingGroupEnrollment(String courseId, String groupNo, String studentEmail) {
        this.courseId = courseId;
        this.groupNo = groupNo;
        this.studentEmail = studentEmail;
    }
}
