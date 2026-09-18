package com.telemedicine.myhealth.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String ex){
        super(ex);
    }
}
