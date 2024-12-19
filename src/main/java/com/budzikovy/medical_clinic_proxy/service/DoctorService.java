package com.budzikovy.medical_clinic_proxy.service;

import com.budzikovy.medical_clinic_proxy.client.MedicalClinicProxy;
import com.budzikovy.medical_clinic_proxy.model.dto.DoctorDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    private final MedicalClinicProxy medicalClinicClient;

    public List<DoctorDto> getDoctors(String specialization, Pageable pageable) {
        logger.info("Fetching doctors with specialization: {} and pageable: {}", specialization, pageable);
        try {
            List<DoctorDto> doctorsWithSpecialization = medicalClinicClient.getDoctors(specialization, pageable);
            logger.info("Successfully fetched {} doctors with specialization: {}", doctorsWithSpecialization.size(), specialization);
            return doctorsWithSpecialization;
        } catch (Exception e) {
            logger.error("Failed to fetch doctors with specialization: {}. Error: {}", specialization, e.getMessage(), e);
            throw e;
        }
    }

}
