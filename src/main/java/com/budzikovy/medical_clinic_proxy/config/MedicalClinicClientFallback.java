//package com.budzikovy.medical_clinic_proxy.config;
//
//import com.budzikovy.medical_clinic_proxy.client.MedicalClinicClient;
//import com.budzikovy.medical_clinic_proxy.model.dto.VisitDto;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class MedicalClinicClientFallback implements MedicalClinicClient {
//
//
//    @Override
//    public List<VisitDto> getVisitsByPatient(Long patientId, int page, int size) {
//        return new ArrayList<>();
//    }
//
//    @Override
//    public VisitDto assignPatientToVisit(Long visitId, Long patientId) {
//        return null;
//    }
//
//    @Override
//    public List<VisitDto> getAvailableVisitsByDoctorId(Long doctorId, int page, int size) {
//        return new ArrayList<>();
//    }
//
//    @Override
//    public List<VisitDto> getAvailableVisitsBySpecializationAndDay(String specialization, String days, int page, int size) {
//        return new ArrayList<>();
//    }
//}
