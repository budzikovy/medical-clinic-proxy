package com.budzikovy.medical_clinic_proxy.client;

import com.budzikovy.medical_clinic_proxy.config.FeignConfig;
import com.budzikovy.medical_clinic_proxy.config.MedicalClinicClientFallback;
import com.budzikovy.medical_clinic_proxy.model.dto.DoctorDto;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "medicalClinicClient", url = "${spring.cloud.openfeign.client.config.medicalClinicClient.url}",
        configuration = FeignConfig.class, fallback = MedicalClinicClientFallback.class)
public interface MedicalClinicProxy {

    @PutMapping("/visits/{visitId}/patient/{patientId}")
    VisitDto assignPatientToVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId);

    @GetMapping("/visits/available")
    List<VisitDto> getAvailableVisits(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false, defaultValue = "1") int days,
            Pageable pageable);

    @GetMapping("/doctors")
    List<DoctorDto> getDoctors(@RequestParam(required = false) String specialization, Pageable pageable);

    @GetMapping("visits")
    List<VisitDto> getVisitsByDateRange(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String specialization,
            Pageable pageable);

    @GetMapping("/visits")
    List<VisitDto> getAvailableVisitsByDateRangeAndSpecialization(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String specialization,
            Pageable pageable);

    @GetMapping("/visits")
    List<VisitDto> getVisits(@RequestParam(required = false) Long patientId,
                             @RequestParam(required = false) Long doctorId,
                             @RequestParam(required = false) String timeFilter,
                             Pageable pageable);

    @DeleteMapping("/visits/{visitId}")
    VisitDto cancelVisit(@PathVariable Long visitId);

}