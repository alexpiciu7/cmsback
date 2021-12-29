package com.project.cms.service;

import com.project.cms.model.Note;
import com.project.cms.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements INoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getAllNotes(String courseId, String studentEmail) {
        return noteRepository.findByCourseIdAndStudentEmail(courseId, studentEmail);
    }
}
