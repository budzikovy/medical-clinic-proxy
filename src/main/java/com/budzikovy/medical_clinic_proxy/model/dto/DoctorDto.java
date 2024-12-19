package com.budzikovy.medical_clinic_proxy.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class DoctorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String specialization;
    private Set<Long> institutionsId;
    private Set<Long> visitsId;

}
