package com.neqrofukk.medical_clinic_proxy.controller;

import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import com.neqrofukk.medical_clinic_proxy.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/doctors")
@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/doctors/{doctorId}/visits")
    Set<VisitDto> getAllDoctorVisits(@PathVariable Long doctorId) {
        return doctorService.getAllDoctorVisits(doctorId);
    }

    @DeleteMapping("/visits/{visitId}/patient")
    VisitDto cancelVisit(@PathVariable Long visitId) {
        return doctorService.cancelVisit(visitId);
    }

}
