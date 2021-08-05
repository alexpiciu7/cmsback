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
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String imageURL;
    @NotNull
    private Duration courseDuration;
    @NotNull
    private Duration registerDuration;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
    @Positive
    private int capacity;
    private boolean isActive;
}
