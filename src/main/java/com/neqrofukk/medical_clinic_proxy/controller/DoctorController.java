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

    // jako doctor:
    // Możliwość zobaczenia wszystkich swoich wizyt -> przeszlych terazniejszych wszystkich
    @GetMapping("/doctors/{doctorId}/visits")
    Set<VisitDto> getAllDoctorVisits(@PathVariable Long doctorId, @RequestParam Boolean available) {
        return doctorService.getAllDoctorVisits(doctorId);
    };

    // Chcialbym miec mozliwosc zrezygnowania z wizyty -> tzn. ktos jest zapisany ale wtedy nie moge wiec chce taka wizyte moc odwolac
    @DeleteMapping("/visits/{visitId}/patient")
    VisitDto cancelVisit(@PathVariable Long visitId) {
        return doctorService.cancelVisit(visitId);
    };
}
