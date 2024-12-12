package com.budzikovy.medical_clinic_proxy.client;

import com.budzikovy.medical_clinic_proxy.config.FeignConfig;
import com.budzikovy.medical_clinic_proxy.config.MedicalClinicClientFallback;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "medicalClinicClient", url = "${spring.cloud.openfeign.client.config.medicalClinicClient.url}",
        configuration = FeignConfig.class, fallback = MedicalClinicClientFallback.class)
public interface MedicalClinicProxy {

    @GetMapping("/visits")
    List<VisitDto> getVisitsByPatient(@RequestParam("patientId") Long patientId, Pageable pageable);

    @PutMapping("/visits/{visitId}/patient/{patientId}")
    VisitDto assignPatientToVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId);

    @GetMapping("visits/available")
    List<VisitDto> getAvailableVisits(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false, defaultValue = "1") int days,
            Pageable pageable);
}
