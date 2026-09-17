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

    // Jako pacjent:
    // Możliwość zapisania się na wizytę
    public VisitDto bookVisit(Long visitId, Long patientId) {
        return client.bookVisit(visitId, patientId);
    }

    // Możliwość sprawdzenia wszystkich dostępnych wizyt dla danego doktora
    public Set<VisitDto> getAllDoctorAvailableVisits(Long doctorId) {
        return client.findAllDoctorVisits(doctorId, true);
    }

    // Sprawdzenia wszystkich terminów dla danego przedzialu czasowego dla danej specjalizacji i bez specjalizacji
    public PageResponse<VisitDto> getVisits(String specialty, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return client.getVisits(specialty, startTime, endTime, pageable);
    }

    // Sprawdzić wszystkich doktórów z danej specjalizacji
    public PageResponse<DoctorDto> getDoctors(String specialty, Pageable pageable) {
        return client.getDoctors(specialty, pageable);
    }
}
