package com.project.cms.payload.response;

import com.project.cms.model.Course;
import com.project.cms.model.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
public class CourseResponse {

    private String id;
    private String name;
    private String description;
    private String imageURL;
    private String courseDuration;
    private String registerDuration;
    private String country;
    private String city;
    private String address;
    private int capacity;
    private boolean isActive;
    public CourseResponse(Course course)
    {
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


    }
}
