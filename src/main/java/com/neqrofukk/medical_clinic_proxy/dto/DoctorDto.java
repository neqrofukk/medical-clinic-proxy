package com.neqrofukk.medical_clinic_proxy.dto;

public record DoctorDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String specialty
) {
}
