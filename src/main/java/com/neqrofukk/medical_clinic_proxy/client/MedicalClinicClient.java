package com.neqrofukk.medical_clinic_proxy.client;

import com.neqrofukk.medical_clinic_proxy.dto.DoctorDto;
import com.neqrofukk.medical_clinic_proxy.dto.PageResponse;
import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@FeignClient(
        value = "medicalClinicClient",
        url = "http://localhost:8081",
        configuration = MedicalClinicClientConfig.class
)
public interface MedicalClinicClient {

    // Ja jako pacjent chciałbym miec możliwość:
    // Zobaczenia wszystkich swoich wizyt
    @GetMapping("/patients/{patientId}/visits")
    Set<VisitDto> findAllPatientVisits(@PathVariable Long patientId);

    // Jako pacjent:
    // Możliwość zapisania się na wizytę
    @PutMapping("/visits/{visitId}/patient/{patientId}")
    VisitDto bookVisit(@PathVariable Long visitId, @PathVariable Long patientId);

    // Możliwość sprawdzenia wszystkich dostępnych wizyt dla danego doktora
    @GetMapping("/doctors/{doctorId}/visits")
    Set<VisitDto> findAllDoctorVisits(@PathVariable Long doctorId, @RequestParam Boolean available);

    // Sprawdzenia wszystkich terminów dla danego przedzialu czasowego dla danej specjalizacji i bez specjalizacji
    @GetMapping("/visits")
    PageResponse<VisitDto> getVisits(@RequestParam String specialty, @RequestParam  LocalDateTime startTime, @RequestParam LocalDateTime endTime, @RequestParam Pageable pageable);

    // Sprawdzić wszystkich doktórów z danej specjalizacji
    @GetMapping("/doctors")
    PageResponse<DoctorDto> getDoctors(@RequestParam String specialty, @PageableDefault(sort = "lastName") Pageable pageable);

    // jako doctor:
    // Możliwość zobaczenia wszystkich swoich wizyt -> przeszlych terazniejszych wszystkich
    // -> findAllDoctorVisits wyżej

    // Chcialbym miec mozliwosc zrezygnowania z wizyty -> tzn. ktos jest zapisany ale wtedy nie moge wiec chce taka wizyte moc odwolac
    @DeleteMapping("/visits/{visitId}/patient")
    VisitDto cancelVisit(@PathVariable Long visitId);

}
