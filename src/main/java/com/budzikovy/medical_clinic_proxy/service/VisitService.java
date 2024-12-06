package com.budzikovy.medical_clinic_proxy.service;

import com.budzikovy.medical_clinic_proxy.client.MedicalClinicClient;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final MedicalClinicClient medicalClinicClient;

    public List<VisitDto> getVisitsByPatient(Long patientId, int page, int size) {
        return medicalClinicClient.getVisitsByPatient(patientId, page, size);
    }

    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        return medicalClinicClient.assignPatientToVisit(visitId, patientId);
    }

    public List<VisitDto> getAvailableVisits(Long doctorId, String specialization, String days, int page, int size) {
        if (doctorId != null) {
            return medicalClinicClient.getAvailableVisitsByDoctorId(doctorId, page, size);
        } else if (specialization != null && days != null) {
            return medicalClinicClient.getAvailableVisitsBySpecializationAndDay(specialization, days, page, size);
        } else {
            throw new IllegalArgumentException("Either doctorId or specialization and day must be provided");
        }
    }

}
