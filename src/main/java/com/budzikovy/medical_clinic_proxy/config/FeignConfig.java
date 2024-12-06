//package com.budzikovy.medical_clinic_proxy.config;
//
//import feign.Retryer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FeignConfig {
//
//    @Bean
//    public Retryer feignRetryer(){
//        return new Retryer.Default(100, 2000, 5); // 5 prób, poczatkowy interwał 0.1s, maksymalny 2s
//    }
//
//}
