package com.project.cms.service;

import com.project.cms.models.Instructor;
import com.project.cms.models.Manager;
import com.project.cms.payload.request.InstructorRegister;
import com.project.cms.payload.request.ManagerRegister;

public interface IAdminService {
    Manager registerManager(ManagerRegister managerRegister);
    Instructor registerInstructor(InstructorRegister instructorRegister);
    boolean activateManagerAccount(String id);
    boolean activateInstructorAccount(String id);
    boolean deactivateManagerAccount(String id);
    boolean deactivateInstructorAccount(String id);
    boolean activateStudentAccount(String id);
    boolean deactivateStudentAccount(String id);
}
