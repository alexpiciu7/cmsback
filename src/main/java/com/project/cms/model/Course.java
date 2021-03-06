package com.project.cms.model;


import com.project.cms.payload.request.CourseRegister;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.*;

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
    private String imageURL;
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
    private String timetable;
    private List<String> post = new ArrayList<>();
    @DBRef
    private Set<Student> students = new HashSet<>();
    private boolean isActive;

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void deleteStudent(Student student) {
        this.students.remove(student);
    }

    public void addPost(String post) {
        this.post.add(post);
    }

    public void updateFields(CourseRegister courseRegister) throws IOException {
        setId(courseRegister.getId());
        setName(courseRegister.getName());
        if (courseRegister.getImage()!=null)
        setImageURL(Base64.getEncoder().encodeToString(courseRegister.getImage().getBytes()));
        if (courseRegister.getCourseStart()!=null&&courseRegister.getCourseEnd()!=null)
        setCourseDuration(new Duration(new Date(courseRegister.getCourseStart()), new Date(courseRegister.getCourseEnd())));
        if (courseRegister.getCourseRegisterStart()!=null&&courseRegister.getCourseRegisterEnd()!=null)
            setRegisterDuration(new Duration(new Date(courseRegister.getCourseRegisterStart()), new Date(courseRegister.getCourseRegisterEnd())));
        setAddress(courseRegister.getAddress());
        setCity(courseRegister.getCity());
        setCountry(courseRegister.getCountry());
        setDescription(courseRegister.getDescription());
        setCapacity(courseRegister.getCapacity());
    }


}
