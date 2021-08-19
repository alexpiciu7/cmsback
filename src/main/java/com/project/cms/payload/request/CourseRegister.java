package com.project.cms.payload.request;

import com.project.cms.model.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class CourseRegister {
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private MultipartFile image;
    @NotBlank
    private String courseStart;
    @NotBlank
    private String courseEnd;
    @NotBlank
    private String courseRegisterStart;
    @NotBlank
    private String courseRegisterEnd;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
    @Positive
    private int capacity;
}
