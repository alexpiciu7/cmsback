package com.project.cms.service;

import com.project.cms.model.Note;

import java.util.List;

public interface INoteService {
    Note save(Note note);
    List<Note> getAllNotes(String courseId,String studentEmail);
}
