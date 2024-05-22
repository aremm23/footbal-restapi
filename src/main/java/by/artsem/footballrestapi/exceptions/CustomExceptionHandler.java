package by.artsem.footballrestapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(DataNotCreatedException e) {
        ErrorResponse ErrorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(DataNotFoundedException e) {
        ErrorResponse ErrorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(NullPointerException e) {
        ErrorResponse ErrorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(ErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
