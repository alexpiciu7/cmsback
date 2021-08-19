package com.project.cms.service;

import com.project.cms.model.PendingCourseEnrollment;
import com.project.cms.model.Student;
import com.project.cms.payload.request.StudentRegister;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    Student register(StudentRegister studentRegister, MultipartFile cv) throws Exception;
    Optional<Student> findOne(String id);
    PendingCourseEnrollment enrollCourse(PendingCourseEnrollment courseEnrollment);


}
