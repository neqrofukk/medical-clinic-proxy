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

    //    // Jako pacjent:
    //    //Zobaczenia wszystkich swoich wizyt
    @GetMapping("/{patientId}/visits")
    public Set<VisitDto> getAllVisits(@PathVariable Long patientId) {
        return patientService.findAllPatientVisits(patientId);
    };

    //    // Możliwość zapisania się na wizytę
    @PutMapping("/visits/{visitId}/patient/{patientId}")
    public VisitDto bookVisit(@PathVariable Long visitId, @PathVariable Long patientId) {
        return patientService.bookVisit(visitId, patientId);
    };

    //    // Możliwość sprawdzenia wszystkich dostępnych wizyt dla danego doktora
    @GetMapping("/doctors/{doctorId}/visits")
    public Set<VisitDto> getAllDoctorAvailableVisits(@PathVariable Long doctorId) {
        return patientService.getAllDoctorAvailableVisits(doctorId);
    };

    //    // Sprawdzenia wszystkich terminów dla danego przedzialu czasowego dla danej specjalizacji i bez specjalizacji
    @GetMapping("/visits")
    public PageResponse<VisitDto> getVisits(@RequestParam String specialty, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @RequestParam Pageable pageable) {
        return patientService.getVisits(specialty, startTime, endTime, pageable);
    };

    //    // Sprawdzić wszystkich doktórów z danej specjalizacji
    @GetMapping("/doctors")
    public PageResponse<DoctorDto> getDoctors(@RequestParam String specialty, @PageableDefault(sort = "lastName") Pageable pageable) {
        return patientService.getDoctors(specialty, pageable);
    };

}
