package com.budzikovy.medical_clinic_proxy.service;

import com.budzikovy.medical_clinic_proxy.client.MedicalClinicClient;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class VisitServiceTest {

    MedicalClinicClient medicalClinicClient;
    VisitService visitService;

    @BeforeEach
    void setup() {
        this.medicalClinicClient = Mockito.mock(MedicalClinicClient.class);
        this.visitService = new VisitService(medicalClinicClient);
    }

    private VisitDto visitDto = VisitDto.builder()
            .id(1L)
            .visitStartTime(LocalDateTime.of(2024, 12,12,18,0))
            .visitEndTime(LocalDateTime.of(2024, 12,12,19,0))
            .doctorId(1L)
            .patientId(null)
            .build();

    Long patientId = 1L;

    Long visitId = 1L;

    Long doctorId = 1L;

    int page = 0;
    int size = 10;

    @Test
    void getVisitsByPatient_DataCorrect_VisitListReturned() {
        List<VisitDto> visits = List.of(visitDto);
        when(medicalClinicClient.getVisitsByPatient(patientId, page, size)).thenReturn(visits);

        List<VisitDto> result = visitService.getVisitsByPatient(patientId, page, size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(visitDto.getId(), result.get(0).getId());
        assertEquals("2024-12-12T18:00", result.get(0).getVisitStartTime().toString());
        assertEquals("2024-12-12T19:00", result.get(0).getVisitEndTime().toString());
    }

    @Test
    void getVisitsByPatient_PatientNotFound_EmptyListReturned() {
        Long patientId = 20L;
        when(medicalClinicClient.getVisitsByPatient(patientId, page, size)).thenReturn(List.of());

        List<VisitDto> result = visitService.getVisitsByPatient(patientId, page, size);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void assignPatientToVisit_DataCorrect_VisitAssigned() {

        VisitDto assignedVisit = VisitDto.builder()
                .id(visitId)
                .visitStartTime(LocalDateTime.of(2024,12,12,18,0))
                .visitEndTime(LocalDateTime.of(2024,12,12,19,0))
                .doctorId(1L)
                .patientId(patientId)
                .build();

        when(medicalClinicClient.assignPatientToVisit(visitId, patientId)).thenReturn(assignedVisit);

        VisitDto result = visitService.assignPatientToVisit(visitId, patientId);

        assertNotNull(result);
        assertEquals(visitId, result.getId());
        assertEquals(patientId, result.getPatientId());
        assertEquals(assignedVisit.getVisitStartTime().toString(), result.getVisitStartTime().toString());
        assertEquals(assignedVisit.getVisitEndTime().toString(), result.getVisitEndTime().toString());
        assertEquals(assignedVisit.getDoctorId(), result.getDoctorId());

    }

    @Test
    void assignPatientToVisit_VisitNotFound_FeignExceptionThrown() {

        Long visitId = 20L;
        when(medicalClinicClient.assignPatientToVisit(visitId, patientId))
                .thenThrow(new RuntimeException("Visit not found"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> visitService.assignPatientToVisit(visitId, patientId));
        assertEquals("Visit not found", exception.getMessage());
    }

    @Test
    void getAvailableVisits_WithDoctorId_DataCorrect_ReturnVisits() {

        List<VisitDto> visits = List.of(visitDto);
        when(medicalClinicClient.getAvailableVisitsByDoctorId(doctorId, page, size)).thenReturn(visits);

        List<VisitDto> result = visitService.getAvailableVisits(doctorId, null, null, page, size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(visitDto.getId(), result.get(0).getId());

    }

    @Test
    void getAvailableVisits_WithSpecializationAndDays_DataCorrect_ReturnVisits() {

        String specialization = "Cardiology";
        String days = "Monday";
        List<VisitDto> visits = List.of(visitDto);
        when(medicalClinicClient.getAvailableVisitsBySpecializationAndDay(specialization, days, page, size)).thenReturn(visits);

        List<VisitDto> result = visitService.getAvailableVisits(null, specialization, days, page, size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(visitDto.getId(), result.get(0).getId());

    }

    @Test
    void getAvailableVisits_WithInvalidParameters_ThrowsIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> visitService.getAvailableVisits(null, null, null, page, size));
        assertEquals("Either doctorId or specialization and day must be provided", exception.getMessage());
    }





}
