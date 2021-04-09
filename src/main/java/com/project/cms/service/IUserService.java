package com.project.cms.service;

import com.project.cms.models.Student;
import com.project.cms.payload.request.LoginRequest;
import com.project.cms.payload.request.StudentSignup;

import java.util.Map;

public interface IUserService {

    Student register(StudentSignup studentSignup) throws Exception;
    Map<Object, Object> login(LoginRequest loginRequest);

}
