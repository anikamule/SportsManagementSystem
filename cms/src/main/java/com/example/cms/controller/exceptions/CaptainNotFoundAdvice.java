package com.example.cms.controller.exceptions;

import com.example.cms.model.entity.Captain;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CaptainNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CaptainNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String captainNotFoundHandler(CaptainNotFoundException ex) {
        return ex.getMessage();
}
}
