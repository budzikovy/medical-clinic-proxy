package com.budzikovy.medical_clinic_proxy.service;

import com.budzikovy.medical_clinic_proxy.client.MedicalClinicClient;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final MedicalClinicClient medicalClinicClient;

    public List<VisitDto> getVisitsByPatient(Long patientId, Pageable pageable) {
        return medicalClinicClient.getVisitsByPatient(patientId, pageable);
    }

    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        return medicalClinicClient.assignPatientToVisit(visitId, patientId);
    }

    public List<VisitDto> getAvailableVisits(Long doctorId, String specialization, int days, Pageable pageable) {
        return medicalClinicClient.getAvailableVisits(doctorId, specialization, days, pageable);
    }
}
