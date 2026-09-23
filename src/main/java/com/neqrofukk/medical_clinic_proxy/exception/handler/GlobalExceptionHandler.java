package com.neqrofukk.medical_clinic_proxy.exception.handler;

import com.neqrofukk.medical_clinic_proxy.exception.InvalidUpstreamRequestException;
import com.neqrofukk.medical_clinic_proxy.exception.MedicalClinicProxyException;
import com.neqrofukk.medical_clinic_proxy.exception.ResourceNotFoundException;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ProblemDetail handleFeignException(FeignException ex) {
        log.error("Handled Feign exception: status = {}, message = {}", ex.status(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(ex.status()),
                ex.contentUTF8()
        );
    }

    @ExceptionHandler(MedicalClinicProxyException.class)
    public ProblemDetail handleMedicalClinicProxyException(MedicalClinicProxyException ex) {
        log.warn("Handled Medical Clinic Proxy exception: status = {}, message = {}", ex.getStatus().value(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("Handled resource not found exception: status = {}, message = {}", ex.getStatus().value(), ex.getMessage());
        return ProblemDetail.forStatusAndDetail(
                ex.getStatus(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidUpstreamRequestException.class)
    public ProblemDetail handleInvalidUpstreamException(InvalidUpstreamRequestException ex) {
        log.warn("Handled invalid upstream request exception: message = {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    @ExceptionHandler(RetryableException.class)
    public ProblemDetail handleRetryUnavailable(RetryableException ex) {
        log.error("Medical Clinic API still unavailable after 3 retries, message = {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Medical Clinic API is currently unavailable"
        );
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception exception) {
        log.error("Unexpected error occurred: ", exception);
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error occurred at " + OffsetDateTime.now());
    }
}
