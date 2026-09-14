package com.neqrofukk.medical_clinic_proxy.client;

import feign.Response;
import feign.codec.ErrorDecoder;

public class MedicalClinicClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
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
        return null;
    }

}
