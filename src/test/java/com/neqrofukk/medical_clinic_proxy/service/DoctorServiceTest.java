package com.neqrofukk.medical_clinic_proxy.service;

import com.neqrofukk.medical_clinic_proxy.client.MedicalClinicClient;
import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DoctorServiceTest {
    MedicalClinicClient client;
    DoctorService service;

    @BeforeEach
    void setup() {
        client = Mockito.mock(MedicalClinicClient.class);
        service = new DoctorService(client);
    }

    @Test
    void getAllDoctorVisits_VisitsExist_VisitsReturned() {
        // given
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        Set<VisitDto> visits = new HashSet<>();
        VisitDto visit1 = new VisitDto(1L, date, date.plusHours(1), 1L, 1L);
        VisitDto visit2 = new VisitDto(2L, date.plusHours(2), date.plusHours(3), 1L, 2L);
        visits.add(visit1);
        visits.add(visit2);
        when(client.findAllDoctorVisits(1L, false)).thenReturn(visits);

        // when
        Set<VisitDto> result = service.getAllDoctorVisits(1L);
        Set<VisitDto> expected = Set.of(visit1, visit2);

        // then
        assertEquals(expected, result);
        verify(client).findAllDoctorVisits(1L, false);
    }

    @Test
    void cancelVisit_VisitExists_PatientRemovedFromVisit() {
        // given
        VisitDto canceledVisit = new VisitDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1), 1L, null);
        when(client.cancelVisit(1L)).thenReturn(canceledVisit);

        // when
        VisitDto result = service.cancelVisit(1L);

        // then
        Assertions.assertAll(
                () -> assertEquals(canceledVisit.id(), result.id()),
                () -> assertEquals(canceledVisit.startTime(), result.startTime()),
                () -> assertEquals(canceledVisit.endTime(), result.endTime()),
                () -> assertEquals(canceledVisit.doctorId(), result.doctorId()),
                () -> assertNull(result.patientId())
        );
    }
}