package com.igladkikh.warehouse.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.igladkikh.warehouse.exception.BadRequestException;
import com.igladkikh.warehouse.exception.DataConflictException;
import com.igladkikh.warehouse.exception.DataNotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({BadRequestException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptionException(Exception e) {
        saveErrorLog(e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(DataNotFoundException e) {
        saveErrorLog(e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataConflictException(DataConflictException e) {
        saveErrorLog(e);
        return new ErrorResponse(HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable e) {
        saveErrorLog(e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), LocalDateTime.now());
    }

    private void saveErrorLog(Throwable e) {
        log.error("Exception message: {}", e.getMessage());
    }
}
