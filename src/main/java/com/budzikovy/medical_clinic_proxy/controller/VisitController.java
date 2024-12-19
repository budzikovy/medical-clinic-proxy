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

    @GetMapping
    public List<VisitDto> getVisits(@RequestParam(required = false) Long patientId,
                                    @RequestParam(required = false) Long doctorId,
                                    @RequestParam(required = false) String timeFilter,
                                    Pageable pageable) {
        return visitService.getVisits(patientId, doctorId, timeFilter, pageable);
    }

    @DeleteMapping("/{visitId}")
    public VisitDto cancelVisit(@PathVariable("visitId") Long visitId) {
        logger.info("Received DELETE request for visit with ID: {}", visitId);
        VisitDto canceledVisit = visitService.cancelVisit(visitId);
        logger.info("Successfully deleted visit with ID: {}", visitId);
        return canceledVisit;
    }

    @GetMapping("/range")
    public List<VisitDto> getVisitsByDateRange(@RequestParam(required = false) String startDate,
                                               @RequestParam(required = false) String endDate,
                                               @RequestParam(required = false) String specialization,
                                               Pageable pageable) {
        logger.info("Received GET request for visits by date range: {} to {}, specialization: {}", startDate, endDate, specialization);
        List<VisitDto> visits = visitService.getVisitsByDateRange(startDate, endDate, specialization, pageable);
        logger.info("Returning {} visits in date range from {} to {}", visits.size(), startDate, endDate);
        return visits;
    }

    @GetMapping("/available/range")
    public List<VisitDto> getAvailableVisitsByDateRangeAndSpecialization(@RequestParam String startDate,
                                                                         @RequestParam String endDate,
                                                                         @RequestParam(required = false) String specialization,
                                                                         Pageable pageable) {
        logger.info("Received GET request for available visits by date range: {} to {}, specialization: {}", startDate, endDate, specialization);
        List<VisitDto> availableVisits = visitService.getAvailableVisitsByDateRangeAndSpecialization(startDate, endDate, specialization, pageable);
        logger.info("Returning {} available visits in date range from {} to {}", availableVisits.size(), startDate, endDate);
        return availableVisits;
    }

}