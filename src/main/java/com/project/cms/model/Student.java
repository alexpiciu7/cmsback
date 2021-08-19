package com.project.cms.model;

import com.project.cms.payload.request.StudentRegister;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;
import java.util.Objects;

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
    public Student(StudentRegister studentRegister, String cv, String password, Role userRole)
    {
        setFName(studentRegister.getFname());
        setLName(studentRegister.getLname());
        setPhone(studentRegister.getPhone());
        setEmail(studentRegister.getEmail());
        setPassword(password);
        setUniversity(studentRegister.getUniversity());
        setYear(studentRegister.getYear());
        setCv(Base64.getEncoder().encodeToString(cv.getBytes()));
        setRole(userRole);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return year == student.year && fName.equals(student.fName) && lName.equals(student.lName) && phone.equals(student.phone) && university.equals(student.university) && cv.equals(student.cv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fName, lName, phone, university, year, cv);
    }
}
