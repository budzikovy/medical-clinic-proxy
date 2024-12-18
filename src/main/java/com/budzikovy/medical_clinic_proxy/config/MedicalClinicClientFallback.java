package com.budzikovy.medical_clinic_proxy.config;

import com.budzikovy.medical_clinic_proxy.client.MedicalClinicProxy;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class MedicalClinicClientFallback implements MedicalClinicProxy {

    @Override
    public List<VisitDto> getVisitsByPatient(Long patientId, Pageable pageable) {
        return emptyList();
    }

    @Override
    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        return null;
    }

    @Override
    public List<VisitDto> getAvailableVisits(Long doctorId, String specialization, int days, Pageable pageable) {
        return emptyList();
    }
}