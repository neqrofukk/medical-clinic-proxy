package com.neqrofukk.medical_clinic_proxy.exception;

import org.springframework.http.HttpStatus;

public class InvalidUpstreamRequestException extends MedicalClinicProxyException {
    public InvalidUpstreamRequestException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
