package com.neqrofukk.medical_clinic_proxy.service;

import com.neqrofukk.medical_clinic_proxy.client.MedicalClinicClient;
import com.neqrofukk.medical_clinic_proxy.dto.DoctorDto;
import com.neqrofukk.medical_clinic_proxy.dto.PageResponse;
import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final MedicalClinicClient client;

    public Set<VisitDto> findAllPatientVisits(Long patientId) {
        return client.findAllPatientVisits(patientId);
    }

    public VisitDto bookVisit(Long visitId, Long patientId) {
        return client.bookVisit(visitId, patientId);
    }

    public Set<VisitDto> getAllDoctorAvailableVisits(Long doctorId) {
        return client.findAllDoctorVisits(doctorId, true);
    }

    public PageResponse<VisitDto> getVisits(String specialty, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return client.getVisits(specialty, startTime, endTime, pageable);
    }

    public PageResponse<DoctorDto> getDoctors(String specialty, Pageable pageable) {
        return client.getDoctors(specialty, pageable);
    }
}
