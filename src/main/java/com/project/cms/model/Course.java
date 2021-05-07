package com.project.cms.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "courses")

public class Course {
    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private ArrayList<String> imageURL = new ArrayList<>();
    @NotNull
    private Duration courseDuration;
    @NotNull
    private Duration registerDuration;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
    @Positive
    private int capacity;
    @DBRef
    private Set<Student> students=new HashSet<>();
    private boolean isActive;
    public void addStudent(Student student)
    {
        this.students.add(student);
    }
    public void deleteStudent(Student student)
    {
        this.students.remove(student);
    }

}
