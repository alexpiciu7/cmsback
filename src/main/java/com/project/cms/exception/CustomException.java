package com.project.cms.exception;

public class CustomException extends Exception{
    private int exceptionId;

    public CustomException(String message, int exceptionId) {
        super(message);
        this.exceptionId = exceptionId;
    }

    public int getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(int exceptionId) {
        this.exceptionId = exceptionId;
    }
}
