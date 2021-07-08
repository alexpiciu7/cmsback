package com.project.cms.service;

import com.project.cms.exception.CustomException;
import com.project.cms.model.Instructor;
import com.project.cms.model.Manager;
import com.project.cms.payload.request.InstructorRegister;
import com.project.cms.payload.request.ManagerRegister;

public interface IAdminService {
    Manager registerManager(ManagerRegister managerRegister);
    Instructor registerInstructor(InstructorRegister instructor);
    boolean activateManagerAccount(String id) throws CustomException;
    boolean activateInstructorAccount(String id) throws CustomException;
    boolean deactivateManagerAccount(String id) throws CustomException;
    boolean deactivateInstructorAccount(String id) throws CustomException;
    boolean activateStudentAccount(String id) throws CustomException;
    boolean deactivateStudentAccount(String id) throws CustomException;
}
