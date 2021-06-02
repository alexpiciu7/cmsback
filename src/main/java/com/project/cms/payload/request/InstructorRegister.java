package com.project.cms.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Setter
@Getter
public class InstructorRegister {
    @NotBlank
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(max = 120)
    private String password;
    @NotBlank
    private String fName;
    @NotBlank
    private String lName;
    @NotBlank
    private String title;
    @NotBlank
    private String phone;
}
