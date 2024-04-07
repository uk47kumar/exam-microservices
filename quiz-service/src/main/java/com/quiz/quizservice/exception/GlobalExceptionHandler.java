package com.quiz.quizservice.exception;

import com.quiz.quizservice.payload.ErrorDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetailResponse> handleBadRequestException(BadRequestException badRequestException,
                                                                         WebRequest webRequest) {
        ErrorDetailResponse response = new ErrorDetailResponse(
                new Date(),
                badRequestException.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                               WebRequest webRequest) {
        ErrorDetailResponse response = new ErrorDetailResponse(
                new Date(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(response, NOT_FOUND);
    }

}
