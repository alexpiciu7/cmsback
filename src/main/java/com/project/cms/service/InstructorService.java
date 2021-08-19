package com.project.cms.service;

import com.project.cms.model.Instructor;
import com.project.cms.repository.InstructorRepository;
import com.project.cms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService implements IInstructorService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Optional<Instructor> findOne(String id) {
        return Optional.of(instructorRepository.findByEmail(id));
    }

    public Instructor save(Instructor instructor)
    {
        return instructorRepository.save(instructor);
    }
}
