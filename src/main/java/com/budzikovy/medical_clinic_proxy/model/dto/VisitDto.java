package com.budzikovy.medical_clinic_proxy.model.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class VisitDto {

    private Long id;
    private LocalDateTime visitStartTime;
    private LocalDateTime visitEndTime;
    private Long doctorId;
    private Long patientId;


}
