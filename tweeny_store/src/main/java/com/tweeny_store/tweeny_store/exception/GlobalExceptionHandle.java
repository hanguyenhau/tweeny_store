package com.tweeny_store.tweeny_store.exception;

import com.tweeny_store.tweeny_store.exception.exceptions.DuplicateEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
