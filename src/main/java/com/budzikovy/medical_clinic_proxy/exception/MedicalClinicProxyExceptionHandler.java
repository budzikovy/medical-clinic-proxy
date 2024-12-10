package com.budzikovy.medical_clinic_proxy.exception;

import com.budzikovy.medical_clinic_proxy.model.dto.ErrorMessageDTO;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class MedicalClinicProxyExceptionHandler {

    @ExceptionHandler(FeignException.Conflict.class)
    public ResponseEntity<ErrorMessageDTO> handleFeignExceptionConflict(FeignException ex) {
        String errorMessage = ex.getMessage();
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(errorMessage, LocalDateTime.now(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ErrorMessageDTO> handleFeignExceptionNotFound(FeignException ex) {
        String errorMessage = ex.getMessage();
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(errorMessage, LocalDateTime.now(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<ErrorMessageDTO> handleFeignExceptionBadRequest(FeignException ex) {
        String errorMessage = ex.getMessage();
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(errorMessage, LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
