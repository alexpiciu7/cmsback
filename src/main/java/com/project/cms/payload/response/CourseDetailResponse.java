package com.project.cms.payload.response;

import com.project.cms.model.Course;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
public class CourseDetailResponse extends CourseResponse{
    private List<String> post;
    private String timetable;

    public CourseDetailResponse(Course course) {
        setId(course.getId());
        setName(course.getName());
        setDescription(course.getDescription());
        setImageURL(course.getImageURL());
        SimpleDateFormat mdyFormat = new SimpleDateFormat("dd.MM.yyyy");
        setCourseDuration(mdyFormat.format(course.getCourseDuration().getStartDate())+"-"+mdyFormat.format(course.getCourseDuration().getEndDate()));
        setRegisterDuration(mdyFormat.format(course.getRegisterDuration().getStartDate())+"-"+mdyFormat.format(course.getRegisterDuration().getEndDate()));
        setCountry(course.getCountry());
        setCity(course.getCity());
        setAddress(course.getAddress());
        setCapacity(course.getCapacity());
        setActive(course.isActive());
        setPost(course.getPost());
    }
}
