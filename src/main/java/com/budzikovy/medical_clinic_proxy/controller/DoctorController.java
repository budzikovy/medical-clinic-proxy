package com.budzikovy.medical_clinic_proxy.controller;

import com.budzikovy.medical_clinic_proxy.model.dto.DoctorDto;
import com.budzikovy.medical_clinic_proxy.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);
    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDto> getDoctors(@RequestParam(required = false) String specialization, Pageable pageable) {
        logger.info("Received GET request for doctors with specialization: {} with pageable: {}", specialization, pageable);
        List<DoctorDto> doctorsWithSpecialization = doctorService.getDoctors(specialization, pageable);
        logger.info("Returning {} visits for patient ID: {}", doctorsWithSpecialization.size(), specialization);
        return doctorsWithSpecialization;
    }

}
