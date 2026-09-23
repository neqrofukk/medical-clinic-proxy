package com.neqrofukk.medical_clinic_proxy.service;

import com.neqrofukk.medical_clinic_proxy.client.MedicalClinicClient;
import com.neqrofukk.medical_clinic_proxy.dto.DoctorDto;
import com.neqrofukk.medical_clinic_proxy.dto.PageResponse;
import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientServiceTest {
    MedicalClinicClient client;
    PatientService service;

    @BeforeEach
    void setup() {
        this.client = Mockito.mock(MedicalClinicClient.class);
        this.service = new PatientService(client);
    }

    @Test
    void findAllPatientVisits_VisitsExist_VisitsReturned() {
        // given
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        VisitDto visit = new VisitDto(1L, start, start.plusHours(1), 1L, 1L);
        Set<VisitDto> visits = Set.of(visit);
        when(client.findAllPatientVisits(1L)).thenReturn(visits);

        // when
        Set<VisitDto> result = service.findAllPatientVisits(1L);

        // then
        Assertions.assertAll(
                () -> assertEquals(visits.size(), result.size()),
                () -> assertTrue(result.containsAll(visits))
        );
        verify(client).findAllPatientVisits(1L);
    }

    @Test
    void bookVisit_VisitExists_VisitReturned() {
        // given
        LocalDateTime date1 = LocalDateTime.now().plusDays(1);
        LocalDateTime date2 = LocalDateTime.now().plusDays(1).plusHours(1);
        VisitDto visit = new VisitDto(1L, date1, date2, 1L, 1L);
        when(client.bookVisit(1L, 1L)).thenReturn(visit);

        // when
        VisitDto result = service.bookVisit(1L, 1L);

        // then
        Assertions.assertAll(
                () -> assertEquals(visit.id(), result.id()),
                () -> assertEquals(visit.startTime(), result.startTime()),
                () -> assertEquals(visit.endTime(), result.endTime()),
                () -> assertEquals(visit.doctorId(), result.doctorId()),
                () -> assertEquals(visit.patientId(), result.patientId())
        );
        verify(client).bookVisit(1L, 1L);
    }

    @Test
    void getAllDoctorAvailableVisits_VisitsExist_VisitsReturned() {
        // given
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        VisitDto visit = new VisitDto(1L, start, start.plusHours(1), 1L, null);
        Set<VisitDto> visits = Set.of(visit);
        when(client.findAllDoctorVisits(1L, true)).thenReturn(visits);

        // when
        Set<VisitDto> result = service.getAllDoctorAvailableVisits(1L);

        // then
        Assertions.assertAll(
                () -> assertEquals(visits.size(), result.size()),
                () -> assertTrue(result.containsAll(visits))
        );
        verify(client).findAllDoctorVisits(1L, true);
    }

    @Test
    void getVisits_VisitsExist_VisitsReturned() {
        // given
        String specialty = "eye doctor";
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        Pageable pageable = PageRequest.of(0, 10);
        VisitDto visit = new VisitDto(1L, start, start.plusHours(1), 1L, 1L);
        PageResponse<VisitDto> pageResponse = new PageResponse<>(List.of(visit), 0, 10, 1L, 1, true);
        when(client.getVisits(specialty, start, end, pageable)).thenReturn(pageResponse);

        // when
        PageResponse<VisitDto> result = service.getVisits(specialty, start, end, pageable);

        // then
        Assertions.assertAll(
                () -> assertEquals(pageResponse.content(), result.content()),
                () -> assertEquals(pageResponse.page(), result.page()),
                () -> assertEquals(pageResponse.size(), result.size()),
                () -> assertEquals(pageResponse.totalElements(), result.totalElements()),
                () -> assertEquals(pageResponse.totalPages(), result.totalPages()),
                () -> assertEquals(pageResponse.last(), result.last())
        );
        verify(client).getVisits(specialty, start, end, pageable);
    }

    @Test
    void getDoctors_DoctorsExist_DoctorsReturned() {
        // given
        String specialty = "eye doctor";
        Pageable pageable = PageRequest.of(0, 10);
        DoctorDto doctor = new DoctorDto(1L, "buziaczek67@serduszko.com", "Jan", "Grzebalski", specialty);
        PageResponse<DoctorDto> pageResponse = new PageResponse<>(List.of(doctor), 0, 10, 1L, 1, true);
        when(client.getDoctors(specialty, pageable)).thenReturn(pageResponse);

        // when
        PageResponse<DoctorDto> result = service.getDoctors(specialty, pageable);

        // then
        Assertions.assertAll(
                () -> assertEquals(pageResponse.content(), result.content()),
                () -> assertEquals(pageResponse.page(), result.page()),
                () -> assertEquals(pageResponse.size(), result.size()),
                () -> assertEquals(pageResponse.totalElements(), result.totalElements()),
                () -> assertEquals(pageResponse.totalPages(), result.totalPages()),
                () -> assertEquals(pageResponse.last(), result.last())
        );
        verify(client).getDoctors(specialty, pageable);
    }
}