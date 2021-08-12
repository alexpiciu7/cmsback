package com.project.cms.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerResponse {

    private String email;
    private String fName;
    private String lName;
    private String phone;
    private boolean isActive;
}
