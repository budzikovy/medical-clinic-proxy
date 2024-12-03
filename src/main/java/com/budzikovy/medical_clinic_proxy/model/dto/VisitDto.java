package com.budzikovy.medical_clinic_proxy.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class VisitDto {

    private Long id;
    private LocalDateTime visitStartTime;
    private LocalDateTime visitEndTime;
    private Long doctorId;
    private Long patientId;

}
