package com.smartexplorer.core.controller;

import com.smartexplorer.core.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author
 * Karol Meksuła
 * 13-06-2018
 * */

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String usernameNotFound() {
        return new UsernameNotFoundException("User not found.").getMessage();
    }

    @ExceptionHandler(UserCreationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userCreationException() {
        return new UserCreationException().getMessage();
    }

    @ExceptionHandler(SpotCreationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String spotCreationException() {
        return new SpotCreationException().getMessage();
    }

    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String cannotUploadFile() {
        return new FileUploadException().getMessage();
    }

    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String cannotDownloadFile() {
        return new FileDownloadException().getMessage();
    }


    @ExceptionHandler(SpotLocalizeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cannotFindSpot() {
        return new SpotLocalizeException().getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalArgument() {
        return "Something went wrong. Argument is invalid.";
    }

}
