package com.project.cms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "instructors")
public class Instructor extends User {
    private String fName;
    private String lName;
    private String title;
    private String phone;
    @DBRef
    private Set<Course> courses=new HashSet<>();
    public void addCourse(Course course)
    {
        courses.add(course);
    }
}
