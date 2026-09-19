package com.neqrofukk.medical_clinic_proxy.client;

import com.neqrofukk.medical_clinic_proxy.dto.DoctorDto;
import com.neqrofukk.medical_clinic_proxy.dto.PageResponse;
import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@FeignClient(
        value = "medicalClinicClient",
        url = "http://localhost:8081",
        configuration = MedicalClinicClientConfig.class,
        fallbackFactory = MedicalClinicClientFallbackFactory.class
)
public interface MedicalClinicClient {

    @GetMapping("/patients/{patientId}/visits")
    Set<VisitDto> findAllPatientVisits(@PathVariable Long patientId);

    @PutMapping("/visits/{visitId}/patient/{patientId}")
    VisitDto bookVisit(@PathVariable Long visitId, @PathVariable Long patientId);

    @GetMapping("/doctors/{doctorId}/visits")
    Set<VisitDto> findAllDoctorVisits(@PathVariable Long doctorId, @RequestParam Boolean available);

    @GetMapping("/visits")
    PageResponse<VisitDto> getVisits(@RequestParam String specialty, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @RequestParam Pageable pageable);

    @GetMapping("/doctors")
    PageResponse<DoctorDto> getDoctors(@RequestParam String specialty, Pageable pageable);

    @DeleteMapping("/visits/{visitId}/patient")
    VisitDto cancelVisit(@PathVariable Long visitId);

}
