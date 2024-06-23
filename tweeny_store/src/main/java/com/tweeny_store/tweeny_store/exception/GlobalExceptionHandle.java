package com.tweeny_store.tweeny_store.exception;

import com.tweeny_store.tweeny_store.exception.exceptions.DuplicateEmailException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.tweeny_store.tweeny_store.exception.BussinessErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException e){
        return ResponseEntity
                .status(ACCOUNT_LOCKED.getHttpStatus())
                .body(ExceptionResponse
                        .builder()
                        .bussinessCode(ACCOUNT_LOCKED.getCode())
                        .bussinessErrorDescription(ACCOUNT_DISABLED.getDescription())
                        .error(e.getMessage())
                        .build());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ExceptionResponse> handleException(DuplicateEmailException e){
        return ResponseEntity
                .status(DUPLICATE_EMAIL.getHttpStatus())
                .body(ExceptionResponse
                        .builder()
                        .bussinessCode(DUPLICATE_EMAIL.getCode())
                        .bussinessErrorDescription(DUPLICATE_EMAIL.getDescription())
                        .error(e.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp){
        //log the exception
        exp.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .bussinessErrorDescription("Internal error, contact the admin")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse
                        .builder()
                        .error(exp.getMessage())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleException(IllegalArgumentException exp){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .bussinessErrorDescription("Internal error, contact the admin")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp){
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessgae = error.getDefaultMessage();
                    errors.add(errorMessgae);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }
}
