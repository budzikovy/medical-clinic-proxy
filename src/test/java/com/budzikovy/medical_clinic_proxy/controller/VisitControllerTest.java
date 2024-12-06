package com.budzikovy.medical_clinic_proxy.controller;

import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import com.budzikovy.medical_clinic_proxy.service.VisitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class VisitControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VisitService visitService;

    VisitDto visitDTO = VisitDto.builder()
            .id(1L)
            .visitStartTime(LocalDateTime.of(2024, 12, 12, 10, 0, 0))
            .visitEndTime(LocalDateTime.of(2024, 12, 12, 10, 15, 0))
            .doctorId(1L)
            .patientId(1L)
            .build();

    VisitDto availableVisit = VisitDto.builder()
            .id(2L)
            .visitStartTime(LocalDateTime.of(2024, 12, 13, 12, 0, 0))
            .visitEndTime(LocalDateTime.of(2024, 12, 13, 12, 15, 0))
            .doctorId(1L)
            .patientId(null)
            .build();

    int page = 0;
    int size = 10;

    @Test
    void getVisitsByPatient_DataCorrect_ReturnStatus200() throws Exception {

        List<VisitDto> visits = List.of(visitDTO);

        when(visitService.getVisitsByPatient(1L, page, size)).thenReturn(visits);

        mockMvc.perform(get("/visits?patientId=1&page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].visitStartTime").value("2024-12-12T10:00:00"))
                .andExpect(jsonPath("$[0].visitEndTime").value("2024-12-12T10:15:00"))
                .andExpect(jsonPath("$[0].doctorId").value(1L))
                .andExpect(jsonPath("$[0].patientId").value(1L));

    }

    @Test
    void assignPatientToVisit_DataCorrect_ReturnStatus200() throws Exception {

        when(visitService.assignPatientToVisit(1L, 1L)).thenReturn(visitDTO);

        mockMvc.perform(put("/visits/1/patient/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.visitStartTime").value("2024-12-12T10:00:00"))
                .andExpect(jsonPath("$.visitEndTime").value("2024-12-12T10:15:00"))
                .andExpect(jsonPath("$.doctorId").value(1L))
                .andExpect(jsonPath("$.patientId").value(1L));

    }

    @Test
    void getAvailableVisits_WithDoctorId_DataCorrect_ReturnStatus200() throws Exception {

        List<VisitDto> availableVisits = List.of(availableVisit);
        when(visitService.getAvailableVisits(1L, null, "1", 0, 10)).thenReturn(availableVisits);

        mockMvc.perform(get("/visits/available?doctorId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].visitStartTime").value("2024-12-13T12:00:00"))
                .andExpect(jsonPath("$[0].visitEndTime").value("2024-12-13T12:15:00"))
                .andExpect(jsonPath("$[0].doctorId").value(1L))
                .andExpect(jsonPath("$[0].patientId").isEmpty());

    }

    @Test
    void getAvailableVisits_WithSpecAndDays_DataCorrect_ReturnStatus200() throws Exception {

        List<VisitDto> availableVisits = List.of(availableVisit);
        when(visitService.getAvailableVisits(null, "surgeon", "10", 0, 10)).thenReturn(availableVisits);

        mockMvc.perform(get("/visits/available?specialization=surgeon&days=10&page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].visitStartTime").value("2024-12-13T12:00:00"))
                .andExpect(jsonPath("$[0].visitEndTime").value("2024-12-13T12:15:00"))
                .andExpect(jsonPath("$[0].doctorId").value(1L))
                .andExpect(jsonPath("$[0].patientId").isEmpty());

    }

}
