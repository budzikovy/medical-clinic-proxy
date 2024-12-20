package com.budzikovy.medical_clinic_proxy.controller;

import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import com.budzikovy.medical_clinic_proxy.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private static final Logger logger = LoggerFactory.getLogger(VisitController.class);
    private final VisitService visitService;

    @GetMapping
    public List<VisitDto> getVisitsByPatient(@RequestParam("patientId") Long patientId, Pageable pageable) {
        logger.info("Received GET request for visits by patient ID: {} with pageable: {}", patientId, pageable);
        List<VisitDto> visits = visitService.getVisitsByPatient(patientId, pageable);
        logger.info("Returning {} visits for patient ID: {}", visits.size(), patientId);
        return visits;
    }

    @PutMapping("/{visitId}/patient/{patientId}")
    public VisitDto assignPatientToVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId) {
        logger.info("Received PUT request to assign patient ID: {} to visit ID: {}", patientId, visitId);
        VisitDto assignedVisit = visitService.assignPatientToVisit(visitId, patientId);
        logger.info("Successfully assigned patient ID: {} to visit ID: {}", patientId, visitId);
        return assignedVisit;
    }

    @GetMapping("/available")
    public List<VisitDto> getAvailableVisits(@RequestParam(value = "doctorId", required = false) Long doctorId,
                                             @RequestParam(value = "specialization", required = false) String specialization,
                                             @RequestParam(value = "days", required = false, defaultValue = "1") int days,
                                             Pageable pageable) {
        logger.info("Received GET request for visits by doctor ID: {}, specialization: {}, days: {} with pageable: {}", doctorId, specialization, days, pageable);
        List<VisitDto> availableVisits = visitService.getAvailableVisits(doctorId, specialization, days, pageable);
        logger.info("Returning {} visits for doctor with ID: {} or specialization: {} and days: {}", availableVisits.size(), doctorId, specialization, days);
        return availableVisits;
    }
}