package com.project.cms.service;

import com.project.cms.models.Role;
import com.project.cms.models.User;
import com.project.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        GrantedAuthority authorities = getUserAuthority(user.getRole());
        return buildUserForAuthentication(user, authorities);
    }

    private GrantedAuthority getUserAuthority(Role userRole) {
        GrantedAuthority role;
        role=new SimpleGrantedAuthority(userRole.getName().name());
        return role;
    }

    private UserDetails buildUserForAuthentication(User user, GrantedAuthority authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singleton(authorities));
    }

}
