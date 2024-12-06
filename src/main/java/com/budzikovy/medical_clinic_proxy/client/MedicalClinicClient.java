package com.budzikovy.medical_clinic_proxy.client;

import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;

@FeignClient(name = "medicalClinicClient", url = "${spring.cloud.openfeign.client.config.medicalClinicClient.url}")
public interface MedicalClinicClient {

    @GetMapping("/visits")
    List<VisitDto> getVisitsByPatient(@RequestParam("patientId") Long patientId,
                                      @RequestParam("page") int page,
                                      @RequestParam("size") int size);

    @PutMapping("/visits/{visitId}/patient/{patientId}")
    VisitDto assignPatientToVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId);

    @GetMapping("/visits/available")
    List<VisitDto> getAvailableVisitsByDoctorId(@RequestParam("doctorId") Long doctorId,
                                                @RequestParam("page") int page,
                                                @RequestParam("size") int size);

    @GetMapping("/visits/available")
    List<VisitDto> getAvailableVisitsBySpecializationAndDay(@RequestParam("specialization") String specialization,
                                                            @RequestParam("days") String days,
                                                            @RequestParam("page") int page,
                                                            @RequestParam("size") int size);

}
