package com.neqrofukk.medical_clinic_proxy.client;

import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class MedicalClinicClientConfig {

    @Bean
    public ErrorDecoder decoder() { return new MedicalClinicClientErrorDecoder(); }

    @Bean
    public Retryer retryer() { return new Retryer.Default(100, 1000, 3); }
}
