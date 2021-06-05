package com.project.cms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note {
    private String instructorId;
    private String studentEmail;
    private String courseId;
    private String note;


}
