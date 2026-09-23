package com.neqrofukk.medical_clinic_proxy.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends MedicalClinicProxyException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
