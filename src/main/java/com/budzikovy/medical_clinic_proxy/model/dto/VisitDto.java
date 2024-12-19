package com.budzikovy.medical_clinic_proxy.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {

    private Long id;
    private LocalDateTime visitStartTime;
    private LocalDateTime visitEndTime;
    private Long doctorId;
    private Long patientId;

}