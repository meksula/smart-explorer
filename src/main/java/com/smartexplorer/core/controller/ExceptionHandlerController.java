package com.smartexplorer.core.controller;

import com.smartexplorer.core.exception.UserCreationException;
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

}
