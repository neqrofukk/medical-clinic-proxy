package com.neqrofukk.medical_clinic_proxy.service;

import com.neqrofukk.medical_clinic_proxy.client.MedicalClinicClient;
import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final MedicalClinicClient client;

    public Set<VisitDto> getAllDoctorVisits(Long doctorId) {
        return client.findAllDoctorVisits(doctorId, false);
    }

    public VisitDto cancelVisit(Long visitId) {
        return client.cancelVisit(visitId);
    }
}
