package com.project.cms.service;

import com.project.cms.model.Instructor;
import com.project.cms.repository.InstructorRepository;
import com.project.cms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService implements IInstructorService {

    private final InstructorRepository instructorRepository;
    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Optional<Instructor> findOne(String id) {
        return Optional.of(instructorRepository.findByEmail(id));
    }

    public Instructor save(Instructor instructor)
    {
        return instructorRepository.save(instructor);
    }
}
