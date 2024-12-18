package com.budzikovy.medical_clinic_proxy.service;

import com.budzikovy.medical_clinic_proxy.client.MedicalClinicProxy;
import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class VisitServiceTest {

    MedicalClinicProxy medicalClinicClient;
    VisitService visitService;

    @BeforeEach
    void setup() {
        this.medicalClinicClient = Mockito.mock(MedicalClinicProxy.class);
        this.visitService = new VisitService(medicalClinicClient);
    }

    private VisitDto visitDto = VisitDto.builder()
            .id(1L)
            .visitStartTime(LocalDateTime.of(2024, 12, 12, 18, 0))
            .visitEndTime(LocalDateTime.of(2024, 12, 12, 19, 0))
            .doctorId(1L)
            .patientId(null)
            .build();

    Long patientId = 1L;
    Long visitId = 1L;
    Long doctorId = 1L;

    Pageable pageable = PageRequest.of(0,10);

    @Test
    void getVisitsByPatient_DataCorrect_VisitListReturned() {
        List<VisitDto> visits = List.of(visitDto);
        when(medicalClinicClient.getVisitsByPatient(patientId, pageable)).thenReturn(visits);

        List<VisitDto> result = visitService.getVisitsByPatient(patientId, pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(visitDto.getId(), result.get(0).getId());
        assertEquals("2024-12-12T18:00", result.get(0).getVisitStartTime().toString());
        assertEquals("2024-12-12T19:00", result.get(0).getVisitEndTime().toString());
    }

    @Test
    void getVisitsByPatient_PatientNotFound_EmptyListReturned() {
        Long patientId = 20L;
        when(medicalClinicClient.getVisitsByPatient(patientId, pageable)).thenReturn(List.of());

        List<VisitDto> result = visitService.getVisitsByPatient(patientId, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void assignPatientToVisit_DataCorrect_VisitAssigned() {

        VisitDto assignedVisit = VisitDto.builder()
                .id(visitId)
                .visitStartTime(LocalDateTime.of(2024, 12, 12, 18, 0))
                .visitEndTime(LocalDateTime.of(2024, 12, 12, 19, 0))
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
    void assignPatientToVisit_VisitNotFound_FeignExceptionNotFoundThrown() {
        Long visitId = 99L;

        when(medicalClinicClient.assignPatientToVisit(visitId, patientId))
                .thenThrow(FeignException.NotFound.class);

        FeignException.NotFound exception = assertThrows(FeignException.NotFound.class,
                () -> visitService.assignPatientToVisit(visitId, patientId));

        assertNotNull(exception);
    }

    @Test
    void getAvailableVisits_WithDoctorId_DataCorrect_ReturnVisits() {

        List<VisitDto> visits = List.of(visitDto);
        when(medicalClinicClient.getAvailableVisits(doctorId, null, 0, pageable)).thenReturn(visits);

        List<VisitDto> result = visitService.getAvailableVisits(doctorId, null, 0, pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(visitDto.getId(), result.get(0).getId());
    }

    @Test
    void getAvailableVisits_WithSpecializationAndDays_DataCorrect_ReturnVisits() {

        String specialization = "surgeon";
        int days = 10;
        List<VisitDto> visits = List.of(visitDto);
        when(medicalClinicClient.getAvailableVisits(null, specialization, days, pageable)).thenReturn(visits);

        List<VisitDto> result = visitService.getAvailableVisits(null, specialization, days, pageable);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(visitDto.getId(), result.get(0).getId());
    }

    @Test
    void getAvailableVisits_WithInvalidParameters_ThrowsFeignExceptionBadRequest() {

        when(medicalClinicClient.getAvailableVisits(null, null, 1, pageable))
                .thenThrow(FeignException.BadRequest.class);

        FeignException.BadRequest exception = assertThrows(FeignException.BadRequest.class,
                () -> visitService.getAvailableVisits(null, null, 1, pageable));

        assertNotNull(exception);
    }
}