package com.project.cms.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "students")
public class Student extends User {
    private String fName;
    private String lName;
    private String phone;
    private String university;
    private int year;
    private String cv;

}
