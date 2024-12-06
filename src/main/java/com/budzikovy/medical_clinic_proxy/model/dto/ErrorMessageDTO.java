package com.budzikovy.medical_clinic_proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDTO {

    private String message;
    private LocalDateTime time;
    private HttpStatus httpStatus;

}
