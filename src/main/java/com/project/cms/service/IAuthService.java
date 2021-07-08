package com.project.cms.service;

import com.project.cms.payload.request.LoginRequest;

import java.util.Map;

public interface IAuthService {

    Map<Object, Object> login(LoginRequest loginRequest);

}
