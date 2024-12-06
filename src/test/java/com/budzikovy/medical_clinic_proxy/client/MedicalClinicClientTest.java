//package com.budzikovy.medical_clinic_proxy.client;
//
//import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.tomakehurst.wiremock.WireMockServer;
//import lombok.NoArgsConstructor;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.time.LocalDateTime;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureWireMock(port = 8090)
//@NoArgsConstructor
//public class MedicalClinicClientTest {
//
//    @Autowired
//    WireMockServer wireMockServer;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    private RestTemplate restTemplate;
//
//    @BeforeEach
//    public void setup(){
//        wireMockServer.start();
//        restTemplate = new RestTemplateBuilder()
//                .rootUri("http://localhost:8091")
//                .build();
//    }
//
//    @AfterEach
//    public void stop(){
//        wireMockServer.stop();
//    }
//
//    private static final Long PATIENT_ID = 1L;
//    private static final int PAGE = 0;
//    private static final int SIZE = 10;
//
//    VisitDto visitDTO = VisitDto.builder()
//            .id(1L)
//            .visitStartTime(LocalDateTime.of(2024, 12, 12, 10, 0, 0))
//            .visitEndTime(LocalDateTime.of(2024, 12, 12, 10, 15, 0))
//            .doctorId(1L)
//            .patientId(1L)
//            .build();
//
//    @Test
//    public void getVisitsByPatient_DataCorrect_VisitsReturned() throws JsonProcessingException {
//
//        wireMockServer.stubFor(get(urlEqualTo("/visits?patientId=1"))
//                .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", "application/json")
//                        .withBody(objectMapper.writeValueAsBytes(visitDTO))));
//
//        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8091")
//             .path("/visits")
//                .queryParam("patientId", PATIENT_ID)
//                .queryParam("page", PAGE)
//                .queryParam("size", SIZE)
//                .toUriString();
//
//        VisitDto result = restTemplate.getForObject(url, VisitDto.class);
//
//        assertThat(result).isNotNull();
//        assertEquals(visitDTO.getDoctorId(), result.getDoctorId());
//        assertEquals(visitDTO.getPatientId(), result.getPatientId());
//    }
//
//    @Test
//    public void assignPatientToVisit_DataCorrect_VisitReturned() throws JsonProcessingException {
//
//    }
//
//    @Test
//    public void getAvailableVisitsByDoctorId_DataCorrect_VisitsReturned() throws JsonProcessingException {
//
//    }
//
//    @Test
//    public void getAvailableVisitsBySpecializationAndDay_DataCorrect_VisitsReturned() throws JsonProcessingException {
//
//    }
//}
