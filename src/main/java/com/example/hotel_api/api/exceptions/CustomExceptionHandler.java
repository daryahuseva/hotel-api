package com.example.hotel_api.api.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException ex, WebRequest request) {
        log.warn("Not found: {}", ex.getMessage());
        ErrorDto errorDto = ErrorDto.builder()
                .error("Not Found")
                .errorDescription(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.warn("Bad request: {}", ex.getMessage());
        ErrorDto errorDto = ErrorDto.builder()
                .error("Bad Request")
                .errorDescription(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorDto errorDto = ErrorDto.builder()
                .error("Internal Server Error")
                .errorDescription("An unexpected error occurred. Please try again later.")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}