package com.budzikovy.medical_clinic_proxy.service;

import com.budzikovy.medical_clinic_proxy.client.MedicalClinicClient;
import com.budzikovy.medical_clinic_proxy.model.dto.DoctorDto;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final MedicalClinicClient medicalClinicClient;

    public List<VisitDto> getVisitsByPatient(Long patientId) {
        return medicalClinicClient.getVisitsByPatient(patientId);
    }

    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        return medicalClinicClient.assignPatientToVisit(visitId, patientId);
    }

    public List<VisitDto> getAvailableVisits(Long doctorId) {
        List<VisitDto> allVisits = medicalClinicClient.getAllVisits();

        return allVisits.stream()
                .filter(visit -> visit.getDoctorId().equals(doctorId) && visit.getPatientId() == null)
                .collect(Collectors.toList());
    }

//    public List<VisitDto> getAvailableVisitsBySpecializationAndDate(String specialization, String date) {
//        List<VisitDto> allVisits = medicalClinicClient.getAllVisits();
//
//        List<DoctorDto> allDoctors = medicalClinicClient.getAllDoctors();
//
//        return allVisits.stream()
//                .filter(visit -> visit.getVisitStartTime().toLocalDate().toString().equals(date)
//                        && visit.getPatientId() == null
//                        && visit.getDoctorId() != null)
//                .filter(visit -> {
//                    DoctorDto doctor = allDoctors.stream()
//                            .filter(d -> d.getId().equals(visit.getDoctorId()))
//                            .findFirst()
//                            .orElse(null);
//
//                    return doctor != null && doctor.getSpecialization().equalsIgnoreCase(specialization);
//                })
//                .collect(Collectors.toList());
//    }

}
