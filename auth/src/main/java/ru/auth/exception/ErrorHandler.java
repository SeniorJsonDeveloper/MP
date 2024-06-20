package ru.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ErrorHandler {



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBody> handle(Exception ex, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorBody> handle(AlreadyExistsException ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex, request);
    }


    public ResponseEntity<ErrorBody> buildResponse(HttpStatus httpStatus, Exception e, WebRequest webRequest) {
        return ResponseEntity
                .status(httpStatus)
                .body(ErrorBody
                        .builder()
                        .message(e.getMessage())
                        .description(webRequest.getDescription(false))
                        .build());
    }
}
