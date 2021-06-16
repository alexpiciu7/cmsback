package com.project.cms.service;

import com.project.cms.model.ERole;
import com.project.cms.payload.request.LoginRequest;
import com.project.cms.repository.RoleRepository;
import com.project.cms.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, RoleRepository roleRepository, BCryptPasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Map<Object, Object> login(LoginRequest loginRequest) {
        try {
            String username = loginRequest.getEmail();
            Authentication au=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));
            String token = jwtUtils.generateJwtToken(username,roleRepository.findByName(ERole.valueOf(au.getAuthorities().stream().findFirst().get().toString())).get() );
            Map<Object, Object> model = new HashMap<>();
            model.put("email", username);
            model.put("roles",roleRepository.findByName(ERole.valueOf(au.getAuthorities().stream().findFirst().get().toString())).get().getName());
            model.put("token", token);
            return model;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }


}
