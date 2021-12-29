package com.project.cms.model;

import com.project.cms.payload.request.StudentRegister;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "students")
public class Student extends User {
    private String fName;
    private String lName;
    private String phone;
    private String university;
    private int year;
    private String cv;
    public Student(StudentRegister studentRegister, String cv, String password, Role userRole)
    {
        setFName(studentRegister.getFname());
        setLName(studentRegister.getLname());
        setPhone(studentRegister.getPhone());
        setEmail(studentRegister.getEmail());
        setPassword(password);
        setUniversity(studentRegister.getUniversity());
        setYear(studentRegister.getYear());
        setCv(cv);
        setRole(userRole);
    }
}
