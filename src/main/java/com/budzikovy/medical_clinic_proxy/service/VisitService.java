package com.budzikovy.medical_clinic_proxy.service;

import com.budzikovy.medical_clinic_proxy.client.MedicalClinicProxy;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private static final Logger logger = LoggerFactory.getLogger(VisitService.class);
    private final MedicalClinicProxy medicalClinicClient;

    public List<VisitDto> getVisitsByPatient(Long patientId, Pageable pageable) {
        logger.info("Fetching visits for patient with ID: {} and pageable: {}", patientId, pageable);
        try {
            List<VisitDto> visits = medicalClinicClient.getVisitsByPatient(patientId, pageable);
            logger.info("Successfully fetched {} visits for patient with ID: {}", visits.size(), patientId);
            return visits;
        } catch (Exception e) {
            logger.error("Failed to fetch visits for patient with ID: {}. Error: {}", patientId, e.getMessage(), e);
            throw e;
        }
    }

    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
        logger.info("Assigning patient with ID: {} to visit with ID: {}", visitId, patientId);
        try {
            VisitDto assignedVisit = medicalClinicClient.assignPatientToVisit(visitId, patientId);
            logger.info("Successfully assigned patient with ID: {} to visit with ID: {}", patientId, visitId);
            return assignedVisit;
        } catch (Exception e) {
            logger.error("Failed to assign patient with ID: {} to visit with ID: {}", patientId, visitId);
            throw e;
        }
    }

    public List<VisitDto> getAvailableVisits(Long doctorId, String specialization, int days, Pageable pageable) {
        logger.info("Fetching available visits for doctor with ID: {} and pageable: {}", doctorId, pageable);
        try {
            List<VisitDto> availableVisits = medicalClinicClient.getAvailableVisits(doctorId, specialization, days, pageable);
            logger.info("Successfully fetched {} available visits with filters - Doctor ID: {}, Specialization: {}, Days: {}",
                    availableVisits.size(), doctorId, specialization, days);
            return availableVisits;
        } catch (Exception e) {
            logger.error("Failed to fetch available visits with filters - Doctor ID: {}, Specialization: {}, Days: {}. Error: {}",
                    doctorId, specialization, days, e.getMessage(), e);
            throw e;
        }
    }
}
