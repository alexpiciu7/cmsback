package com.project.cms.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDetailResponse extends CourseResponse{
    private List<String> post;
    private String timetable;
}
