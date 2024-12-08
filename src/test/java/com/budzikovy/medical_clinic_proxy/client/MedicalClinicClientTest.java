package com.budzikovy.medical_clinic_proxy.client;

import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8091)
@NoArgsConstructor
public class MedicalClinicClientTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    ObjectMapper objectMapper;

    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        wireMockServer.start();
        restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8091")
                .build();
    }

    @AfterEach
    public void stop() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    VisitDto visitDTO = VisitDto.builder()
            .id(1L)
            .visitStartTime(LocalDateTime.of(2024, 12, 12, 10, 0, 0))
            .visitEndTime(LocalDateTime.of(2024, 12, 12, 10, 15, 0))
            .doctorId(1L)
            .patientId(1L)
            .build();

    VisitDto availableVisit = VisitDto.builder()
            .id(2L)
            .visitStartTime(LocalDateTime.of(2024, 12, 13, 10, 0, 0))
            .visitEndTime(LocalDateTime.of(2024, 12, 13, 10, 15, 0))
            .doctorId(1L)
            .patientId(null)
            .build();

    @Test
    public void getVisitsByPatient_DataCorrect_VisitsReturned() throws JsonProcessingException {

        wireMockServer.stubFor(get(urlEqualTo("/visits?patientId=1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsBytes(visitDTO))));

        String url = "http://localhost:8091/visits?patientId=1";

        ResponseEntity<VisitDto> response = restTemplate.getForEntity(url, VisitDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto result = response.getBody();
        assertNotNull(result);
        assertEquals(result.getId(), visitDTO.getId());
        assertEquals(result.getDoctorId(), visitDTO.getDoctorId());
        assertEquals(result.getPatientId(), visitDTO.getPatientId());
        assertEquals(result.getVisitStartTime(), visitDTO.getVisitStartTime());
        assertEquals(result.getVisitEndTime(), visitDTO.getVisitEndTime());
    }

    @Test
    public void assignPatientToVisit_DataCorrect_VisitReturned() throws JsonProcessingException {

        VisitDto visitDTO2 = VisitDto.builder()
                .id(6L)
                .visitStartTime(LocalDateTime.of(2024, 12, 14, 10, 0, 0))
                .visitEndTime(LocalDateTime.of(2024, 12, 14, 10, 15, 0))
                .doctorId(1L)
                .patientId(1L)
                .build();

        wireMockServer.stubFor(put("/visits/6/patient/1")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(visitDTO2))));

        String url = "http://localhost:8091/visits/6/patient/1";

        ResponseEntity<VisitDto> response = restTemplate.exchange(url, HttpMethod.PUT, null, VisitDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto result = response.getBody();
        assertNotNull(result);
        assertEquals(result.getId(), visitDTO2.getId());
        assertEquals(result.getDoctorId(), visitDTO2.getDoctorId());
        assertEquals(result.getPatientId(), visitDTO2.getPatientId());
        assertEquals(result.getVisitStartTime(), visitDTO2.getVisitStartTime());
        assertEquals(result.getVisitEndTime(), visitDTO2.getVisitEndTime());
    }

    @Test
    public void getAvailableVisitsByDoctorId_DataCorrect_VisitsReturned() throws JsonProcessingException {

        wireMockServer.stubFor(get("/visits/available?doctorId=1")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(availableVisit))));

        String url = "http://localhost:8091/visits/available?doctorId=1";

        ResponseEntity<VisitDto> response = restTemplate.getForEntity(url, VisitDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto result = response.getBody();
        assertNotNull(result);
        assertEquals(result.getId(), availableVisit.getId());
        assertEquals(result.getDoctorId(), availableVisit.getDoctorId());
        assertEquals(result.getPatientId(), availableVisit.getPatientId());
        assertEquals(result.getVisitStartTime(), availableVisit.getVisitStartTime());
        assertEquals(result.getVisitEndTime(), availableVisit.getVisitEndTime());
    }

    @Test
    public void getAvailableVisitsBySpecializationAndDay_DataCorrect_VisitsReturned() throws JsonProcessingException {

        wireMockServer.stubFor(get(urlEqualTo(
                "/visits/available?specialization=surgeon&days=10&page=0&size=10"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(availableVisit))));

        String url = "http://localhost:8091/visits/available?specialization=surgeon&days=10&page=0&size=10";

        ResponseEntity<VisitDto> response = restTemplate.getForEntity(url, VisitDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto result = response.getBody();
        assertNotNull(result);
        assertEquals(result.getId(), availableVisit.getId());
        assertEquals(result.getDoctorId(), availableVisit.getDoctorId());
        assertEquals(result.getPatientId(), availableVisit.getPatientId());
        assertEquals(result.getVisitStartTime(), availableVisit.getVisitStartTime());
        assertEquals(result.getVisitEndTime(), availableVisit.getVisitEndTime());
    }
}
