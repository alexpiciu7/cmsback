package com.project.cms.payload.request;

import com.project.cms.model.Duration;
import com.project.cms.model.Student;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
