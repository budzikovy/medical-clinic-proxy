package com.budzikovy.medical_clinic_proxy.exception;

public class VisitAlreadyTakenException extends RuntimeException {
    public VisitAlreadyTakenException(Long doctorId) {
        super(String.format("Doctor with id: %s, already have visit on this date, input another date", doctorId));

    }
}
