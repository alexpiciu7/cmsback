package com.project.cms.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note {
    private String lecturerId;
    private String studentId;
    private String courseId;
    private String note;


}
