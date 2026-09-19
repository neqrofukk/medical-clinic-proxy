package com.neqrofukk.medical_clinic_proxy.controller;

import com.neqrofukk.medical_clinic_proxy.dto.DoctorDto;
import com.neqrofukk.medical_clinic_proxy.dto.PageResponse;
import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import com.neqrofukk.medical_clinic_proxy.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RequestMapping("/patients")
@RestController
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/{patientId}/visits")
    public Set<VisitDto> getAllVisits(@PathVariable Long patientId) {
        return patientService.findAllPatientVisits(patientId);
    }

    @PutMapping("/visits/{visitId}/patient/{patientId}")
    public VisitDto bookVisit(@PathVariable Long visitId, @PathVariable Long patientId) {
        return patientService.bookVisit(visitId, patientId);
    }

    @GetMapping("/doctors/{doctorId}/visits")
    public Set<VisitDto> getAllDoctorAvailableVisits(@PathVariable Long doctorId) {
        return patientService.getAllDoctorAvailableVisits(doctorId);
    }

    @GetMapping("/visits")
    public PageResponse<VisitDto> getVisits(@RequestParam String specialty, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @RequestParam Pageable pageable) {
        return patientService.getVisits(specialty, startTime, endTime, pageable);
    }

    @GetMapping("/doctors")
    public PageResponse<DoctorDto> getDoctors(@RequestParam String specialty, @PageableDefault(sort = "lastName") Pageable pageable) {
        return patientService.getDoctors(specialty, pageable);
    }

}
