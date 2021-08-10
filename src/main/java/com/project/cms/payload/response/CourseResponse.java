package com.project.cms.payload.response;

import com.project.cms.model.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
@Getter
@Setter
@NoArgsConstructor
public class CourseResponse {

    private String id;
    private String name;
    private String description;
    private String imageURL;
    private Duration courseDuration;
    private Duration registerDuration;
    private String country;
    private String city;
    private String address;
    private int capacity;
    private boolean isActive;
}
