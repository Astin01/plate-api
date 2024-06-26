package com.project.plateapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    // 400
    @ExceptionHandler({
            RuntimeException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Object> BadRequestException(final RuntimeException ex) {
        log.warn("error", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // 401
    @ExceptionHandler({Unauthorized.class})
    public ResponseEntity<?> handleAccessDeniedException(final Unauthorized ex) {
        log.warn("error", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
