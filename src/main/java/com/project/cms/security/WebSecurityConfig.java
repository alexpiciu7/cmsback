package com.project.cms.security;


import com.project.cms.security.jwt.AuthEntryPointJwt;
import com.project.cms.security.jwt.JwtConfigurer;
import com.project.cms.security.jwt.JwtUtils;
import com.project.cms.service.UserDetailsServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private JwtUtils jwtTokenProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = mongoUserDetails();
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized");
    }
    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST,"/api/student/register").permitAll()
                .antMatchers(HttpMethod.POST,"/api/auth/login").permitAll()
                .antMatchers(HttpMethod.POST,"/*").permitAll()
                .antMatchers(HttpMethod.POST,"/instructor/add/course").permitAll()
                .antMatchers(HttpMethod.PUT,"/student/course").permitAll()
                .antMatchers(HttpMethod.PUT,"/student/school").permitAll()
                .antMatchers(HttpMethod.PUT,"/teacher/school").permitAll()
                .antMatchers(HttpMethod.PUT,"/teacher/course").permitAll()
                .antMatchers(HttpMethod.GET,"/api/student/course/all").permitAll()
                .antMatchers(HttpMethod.GET,"/api/student/course/*").permitAll()
                .antMatchers(HttpMethod.GET,"/login").permitAll()
                .antMatchers(HttpMethod.GET,"/course/all").permitAll()
                .antMatchers(HttpMethod.DELETE,"/course/delete").permitAll()
                .antMatchers(HttpMethod.DELETE,"/student/delete").permitAll()
                .antMatchers(HttpMethod.DELETE,"/teacher/delete").permitAll()
                .antMatchers(HttpMethod.DELETE,"/course/delete").permitAll()
                .antMatchers(HttpMethod.DELETE,"/schooladmin/delete").permitAll()
                .antMatchers(HttpMethod.DELETE,"/school/delete").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
                .cors().and().csrf().disable().authorizeRequests()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }


    @Bean
    public UserDetailsService mongoUserDetails() {
        return new UserDetailsServiceImpl();
    }
}