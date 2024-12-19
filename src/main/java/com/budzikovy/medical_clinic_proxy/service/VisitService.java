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

    public List<VisitDto> getVisits(Long patientId, Long doctorId, String timeFilter, Pageable pageable) {
        logger.info("Fetching visits for Patient ID: {}, Doctor ID: {}, Time Filter: {}", patientId, doctorId, timeFilter);
        try {
            List<VisitDto> visits = medicalClinicClient.getVisits(patientId, doctorId, timeFilter, pageable);
            logger.info("Successfully fetched {} visits for Patient ID: {} and Doctor ID: {}", visits.size(), patientId, doctorId);
            return visits;
        } catch (Exception e) {
            logger.error("Failed to fetch visits for Patient ID: {}, Doctor ID: {}, Time Filter: {}. Error: {}",
                    patientId, doctorId, timeFilter, e.getMessage(), e);
            throw e;
        }
    }

    public VisitDto cancelVisit(Long visitId) {
        logger.info("Canceling visit with ID: {}", visitId);
        try {
            VisitDto canceledVisit = medicalClinicClient.cancelVisit(visitId);
            logger.info("Successfully canceled visit with ID: {}", visitId);
            return canceledVisit;
        } catch (Exception e) {
            logger.error("Failed to cancel visit with ID: {}. Error: {}", visitId, e.getMessage(), e);
            throw e;
        }
    }

    public List<VisitDto> getVisitsByDateRange(String startDate, String endDate, String specialization, Pageable pageable) {
        logger.info("Fetching visits by date range from {} to {} with specialization: {}", startDate, endDate, specialization);
        try {
            List<VisitDto> visits = medicalClinicClient.getVisitsByDateRange(startDate, endDate, specialization, pageable);
            logger.info("Successfully fetched {} visits in date range from {} to {}", visits.size(), startDate, endDate);
            return visits;
        } catch (Exception e) {
            logger.error("Failed to fetch visits by date range from {} to {}. Error: {}", startDate, endDate, e.getMessage(), e);
            throw e;
        }
    }

    public List<VisitDto> getAvailableVisitsByDateRangeAndSpecialization(String startDate, String endDate, String specialization, Pageable pageable) {
        logger.info("Fetching available visits by date range from {} to {} with specialization: {}", startDate, endDate, specialization);
        try {
            List<VisitDto> availableVisits = medicalClinicClient.getAvailableVisitsByDateRangeAndSpecialization(startDate, endDate, specialization, pageable);
            logger.info("Successfully fetched {} available visits in date range from {} to {}", availableVisits.size(), startDate, endDate);
            return availableVisits;
        } catch (Exception e) {
            logger.error("Failed to fetch available visits by date range from {} to {}. Error: {}", startDate, endDate, e.getMessage(), e);
            throw e;
        }
    }
}
