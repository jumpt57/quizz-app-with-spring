package com.jumpt57.quizz.config;

import com.amazonaws.AmazonClientException;
import com.jumpt57.quizz.exceptions.UnknownRoleException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UnknownRoleException.class})
    protected ResponseEntity<String> handleUnkownRoleException(final UnknownRoleException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AmazonClientException.class})
    protected ResponseEntity<String> handleAmazonClientException(final AmazonClientException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        logger.info(ex.getMessage());

        return handleExceptionInternal(ex, ex.getMessage(), headers, HttpStatus.BAD_REQUEST, request);
    }

}
