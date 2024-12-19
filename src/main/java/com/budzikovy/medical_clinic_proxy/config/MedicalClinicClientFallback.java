package com.budzikovy.medical_clinic_proxy.config;

import com.budzikovy.medical_clinic_proxy.client.MedicalClinicProxy;
import com.budzikovy.medical_clinic_proxy.model.dto.DoctorDto;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class MedicalClinicClientFallback implements MedicalClinicProxy {

    @Override
    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        return new VisitDto();
    }

    @Override
    public List<VisitDto> getAvailableVisits(Long doctorId, String specialization, int days, Pageable pageable) {
        return emptyList();
    }

    @Override
    public List<DoctorDto> getDoctors(String specialization, Pageable pageable) {
        return emptyList();
    }

    @Override
    public List<VisitDto> getVisitsByDateRange(String startDate, String endDate, String specialization, Pageable pageable) {
        return emptyList();
    }

    @Override
    public List<VisitDto> getAvailableVisitsByDateRangeAndSpecialization(String startDate, String endDate, String specialization, Pageable pageable) {
        return emptyList();
    }

    @Override
    public List<VisitDto> getVisits(Long patientId, Long doctorId, String timeFilter, Pageable pageable) {
        return emptyList();
    }

    @Override
    public VisitDto cancelVisit(Long visitId) {
        return new VisitDto();
    }

}