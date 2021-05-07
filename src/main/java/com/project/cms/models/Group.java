package com.project.cms.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Group {

    private String course_id;
    private int groupNo;
    private int capacity;
    @DBRef
    private Set<Student> students=new HashSet<>();

}
