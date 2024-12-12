package com.budzikovy.medical_clinic_proxy.client;

import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
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
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8089)
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
                .rootUri("http://localhost:8090")
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
        wireMockServer.stubFor(get(urlEqualTo("/visits?patientId=1&size=10&page=0"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsBytes(List.of(visitDTO)))));

        String url = "/visits?patientId=1&size=10&page=0";

        ResponseEntity<VisitDto[]> response = restTemplate.getForEntity(url, VisitDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto[] result = response.getBody();
        assertNotNull(result);
        assertEquals(result[0].getId(), visitDTO.getId());
        assertEquals(result[0].getDoctorId(), visitDTO.getDoctorId());
        assertEquals(result[0].getPatientId(), visitDTO.getPatientId());
        assertEquals(result[0].getVisitStartTime(), visitDTO.getVisitStartTime());
        assertEquals(result[0].getVisitEndTime(), visitDTO.getVisitEndTime());
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

        String url = "http://localhost:8090/visits/6/patient/1";

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

        VisitDto availableVisit = VisitDto.builder()
                .id(2L)
                .visitStartTime(LocalDateTime.of(2024, 12, 13, 10, 0, 0))
                .visitEndTime(LocalDateTime.of(2024, 12, 13, 10, 15, 0))
                .doctorId(1L)
                .patientId(null)
                .build();

        wireMockServer.stubFor(get("/visits/available?doctorId=1&days=1&size=10&page=0")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(List.of(availableVisit)))));

        String url = "/visits/available?doctorId=1&days=1&size=10&page=0";

        ResponseEntity<VisitDto[]> response = restTemplate.getForEntity(url, VisitDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto[] result = response.getBody();
        assertNotNull(result);
        assertEquals(result[0].getId(), availableVisit.getId());
        assertEquals(result[0].getDoctorId(), availableVisit.getDoctorId());
        assertEquals(result[0].getPatientId(), availableVisit.getPatientId());
        assertEquals(result[0].getVisitStartTime(), availableVisit.getVisitStartTime());
        assertEquals(result[0].getVisitEndTime(), availableVisit.getVisitEndTime());
    }

    @Test
    public void getAvailableVisitsBySpecializationAndDay_DataCorrect_VisitsReturned() throws JsonProcessingException {

        wireMockServer.stubFor(get(urlEqualTo(
                "/visits/available?specialization=surgeon&days=10&size=10&page=0"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(List.of(availableVisit)))));

        String url = "/visits/available?specialization=surgeon&days=10&size=10&page=0";

        ResponseEntity<VisitDto[]> response = restTemplate.getForEntity(url, VisitDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto[] result = response.getBody();
        assertNotNull(result);
        assertEquals(result[0].getId(), availableVisit.getId());
        assertEquals(result[0].getDoctorId(), availableVisit.getDoctorId());
        assertEquals(result[0].getPatientId(), availableVisit.getPatientId());
        assertEquals(result[0].getVisitStartTime(), availableVisit.getVisitStartTime());
        assertEquals(result[0].getVisitEndTime(), availableVisit.getVisitEndTime());
    }

    @Test
    public void getAvailableVisitsWithRetry_503_RetryExceptionThrown () throws JsonProcessingException {

        wireMockServer.stubFor(get(urlEqualTo("/visits/available?doctorId=1&days=1&size=10&page=0"))
                .inScenario("Retry")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader(HttpHeaders.RETRY_AFTER, "1"))
                .willSetStateTo("Attempt 1")
        );

        wireMockServer.stubFor(get(urlEqualTo("/visits/available?doctorId=1&days=1&size=10&page=0"))
                .inScenario("Retry")
                .whenScenarioStateIs("Attempt 1")
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader(HttpHeaders.RETRY_AFTER, "1"))
                .willSetStateTo("Success"));

        wireMockServer.stubFor(get(urlEqualTo("/visits/available?doctorId=1&days=1&size=10&page=0"))
                .inScenario("Retry")
                .whenScenarioStateIs("Success")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(List.of(availableVisit)))));

        String url = "/visits/available?doctorId=1&days=1&size=10&page=0";

        assertThrows(Exception.class, () -> restTemplate.getForEntity(url, visitDTO.getClass()));

        ResponseEntity<VisitDto[]> response = restTemplate.getForEntity(url, VisitDto[].class);

        verify(4, getRequestedFor(urlEqualTo("/visits/available?doctorId=1&days=1&size=10&page=0")));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        VisitDto[] result = response.getBody();
        assertNotNull(result);
        assertEquals(result[0].getId(), availableVisit.getId());
        assertEquals(result[0].getDoctorId(), availableVisit.getDoctorId());
        assertEquals(result[0].getPatientId(), availableVisit.getPatientId());
        assertEquals(result[0].getVisitStartTime(), availableVisit.getVisitStartTime());
        assertEquals(result[0].getVisitEndTime(), availableVisit.getVisitEndTime());
    }

    @Test
    public void RetryOn503Error (){
        wireMockServer.stubFor(get("/visits/available?doctorId=1&days=1&size=10&page=0")
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader(HttpHeaders.RETRY_AFTER, "1")));

        String url = "/visits/available?doctorId=1&days=1&size=10&page=0";

        assertThrows(Exception.class, () -> restTemplate.getForEntity(url, visitDTO.getClass()));

        verify(3, getRequestedFor(urlEqualTo("/visits/available?doctorId=1&days=1&size=10&page=0")));
    }

    @Test
    public void RetryOn500Error (){
        wireMockServer.stubFor(get("/visits/available?doctorId=1&days=1&size=10&page=0")
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader(HttpHeaders.RETRY_AFTER, "1")));

        String url = "/visits/available?doctorId=1&days=1&size=10&page=0";

        assertThrows(Exception.class, () -> restTemplate.getForEntity(url, visitDTO.getClass()));

        verify(3, getRequestedFor(urlEqualTo("/visits/available?doctorId=1&days=1&size=10&page=0")));
    }

}