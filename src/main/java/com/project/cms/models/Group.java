package com.project.cms.models;


import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public class Group {

    private String course_id;
    private int groupNo;
    private int capacity;
    @DBRef
    private List<User> students;

    public Group() {
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
