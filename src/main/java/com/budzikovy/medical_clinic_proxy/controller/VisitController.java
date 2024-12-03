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
    public List<VisitDto> getVisitsByPatient(@RequestParam("patientId") Long patientId) {
        return visitService.getVisitsByPatient(patientId);
    }

    @PutMapping("/{visitId}/patient/{patientId}")
    public VisitDto assignPatientToVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId) {
        return visitService.assignPatientToVisit(visitId, patientId);
    }

    @GetMapping("/available")
    public List<VisitDto> getAvailableVisits(@RequestParam("doctorId") Long doctorId) {
        return visitService.getAvailableVisits(doctorId);
    }

//    @GetMapping("/by-specialization-and-date")
//    public List<VisitDto> getAvailableVisitsBySpecializationAndDate(
//            @RequestParam("specialization") String specialization,
//            @RequestParam("date") String date) {
//        return visitService.getAvailableVisitsBySpecializationAndDate(specialization, date);
//    }

}
