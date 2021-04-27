package com.project.cms.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "courses")

public class Course {
    @Id
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
