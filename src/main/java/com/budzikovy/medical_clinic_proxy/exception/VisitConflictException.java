package com.budzikovy.medical_clinic_proxy.exception;

public class VisitConflictException extends RuntimeException {
    public VisitConflictException() {
        super("Visit is already assigned to another patient.");
    }
}
