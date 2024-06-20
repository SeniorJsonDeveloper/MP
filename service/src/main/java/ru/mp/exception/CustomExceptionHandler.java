package ru.mp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    private ResponseEntity<ExceptionBody> buildResponse(HttpStatus httpStatus, Exception e, WebRequest request) {
        return ResponseEntity
                .status(httpStatus)
                .body(ExceptionBody.builder()
                        .message(e.getMessage())
                        .description(request.getDescription(false))
                        .build());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handle(Exception e, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionBody> handle(NotFoundException e, WebRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, e, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionBody> handle(BadRequestException e, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, e, request);
    }
}
