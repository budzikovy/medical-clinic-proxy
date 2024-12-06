package com.budzikovy.medical_clinic_proxy.controller;

import com.budzikovy.medical_clinic_proxy.model.dto.DoctorDto;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import com.budzikovy.medical_clinic_proxy.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @GetMapping
    public List<VisitDto> getVisitsByPatient(@RequestParam("patientId") Long patientId,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return visitService.getVisitsByPatient(patientId, page, size);
    }

    @PutMapping("/{visitId}/patient/{patientId}")
    public VisitDto assignPatientToVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId) {
        return visitService.assignPatientToVisit(visitId, patientId);
    }

    @GetMapping("/available")
    public List<VisitDto> getAvailableVisits(@RequestParam(value = "doctorId", required = false) Long doctorId,
                                             @RequestParam(value = "specialization", required = false) String specialization,
                                             @RequestParam(value = "days", required = false, defaultValue = "1") String days,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return visitService.getAvailableVisits(doctorId, specialization, days, page, size);
    }

}
