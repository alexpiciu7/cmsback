package com.project.cms.payload.request;

import com.project.cms.model.Duration;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

@Getter
@Setter
public class CourseRegister {
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private ArrayList<String> imageURL = new ArrayList<>();
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
