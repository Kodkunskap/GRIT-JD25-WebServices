package com.example.demo.controller;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.OffsetDateTime;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Validation Failed");
        pd.setDetail("One or more fields are invalid");
        var errors = e.getBindingResult().getFieldErrors().stream()
                .map( fe -> Map.of(
                        "field", fe.getField(),
                        "rejectedValue", fe.getRejectedValue(),
                        "message", fe.getDefaultMessage()
                ))
                .toList();
        pd.setProperty("errors", errors);
        pd.setProperty("timestamp", OffsetDateTime.now());

        return pd;  // RFC7807 - Problem JSON
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(
            ConstraintViolationException e
    ) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Constraint Violation");
        pd.setDetail("Request parameters/path variables are invalid");
        var error = e.getConstraintViolations().stream()
                .map( cv -> Map.of(
                        "property", cv.getPropertyPath().toString(),
                        "invalidValue", cv.getInvalidValue().toString(),
                        "message", cv.getMessage()
                ))
                .toList();
        pd.setProperty("errors", error);
        pd.setProperty("timestamp", OffsetDateTime.now());
        return pd;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail handleHandlerMethodValidationException(
            HandlerMethodValidationException e
    ) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Constraint Violation");
        pd.setDetail("Request parameters/path variables are invalid");
        var errors = e.getParameterValidationResults().stream()
                .flatMap( r -> r.getResolvableErrors().stream()
                        .map( err -> Map.of(
                                "parameter", r.getMethodParameter().getParameterName(),
                                "invalidValue", String.valueOf(r.getArgument()),
                                "message", err.getDefaultMessage()
                        )))
                .toList();
        pd.setProperty("errors", errors);
        pd.setProperty("timestamp", OffsetDateTime.now());
        return pd;
    }



}
