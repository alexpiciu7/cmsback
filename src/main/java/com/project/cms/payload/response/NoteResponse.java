package com.project.cms.payload.response;

import com.project.cms.model.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {
    private String instructorEmail;
    private String note;
    public NoteResponse(Note note)
    {
        this.instructorEmail=note.getInstructorId();
        this.note=note.getNote();

    }
}
