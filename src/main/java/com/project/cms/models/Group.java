package com.project.cms.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Group {

    private String course_id;
    private int groupNo;
    private int capacity;
    @DBRef
    private List<User> students;

}
