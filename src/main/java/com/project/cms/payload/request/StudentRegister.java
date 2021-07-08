package com.project.cms.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class StudentRegister extends SignupRequest {
    @NotBlank
    private String university;
    @Positive
    private int year;
    private MultipartFile cv;

}
