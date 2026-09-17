package com.neqrofukk.medical_clinic_proxy.client;

import com.neqrofukk.medical_clinic_proxy.exception.InvalidUpstreamRequestException;
import com.neqrofukk.medical_clinic_proxy.exception.ResourceNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class MedicalClinicClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        FeignException feignException = feign.FeignException.errorStatus(methodKey, response);

        return switch (status) {
            case BAD_REQUEST -> new InvalidUpstreamRequestException("Invalid request: " + feignException.contentUTF8());
            case NOT_FOUND -> new ResourceNotFoundException("Resource not found: " + feignException.contentUTF8());
            case SERVICE_UNAVAILABLE -> new RetryableException(status.value(), feignException.getMessage(), response.request().httpMethod(), feignException, (Long) null, response.request());
            default -> feignException;
        };

//        TODO
//
//        if (response.status() == 404) {
//            if (methodKey.contains("getClinic")) {
//                return new ClinicNotFoundException();
//            }
//
//            if (methodKey.contains("getDoctor")) {
//                return new DoctorNotFoundException();
//            }
//        }
//
//        if (response.status() == 409) {
//            return new VisitAlreadyTakenException();
//        }
//
//        return defaultDecoder.decode(methodKey, response);
    }

}
