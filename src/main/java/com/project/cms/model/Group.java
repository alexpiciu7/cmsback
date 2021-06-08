package com.project.cms.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Group {

    private String courseId;
    private String groupNo;
    private int capacity;
    @DBRef
    private Set<Student> students=new HashSet<>();
    public void addStudent(Student student){students.add(student); }

}
