package com.neqrofukk.medical_clinic_proxy.controller;

import com.neqrofukk.medical_clinic_proxy.dto.DoctorDto;
import com.neqrofukk.medical_clinic_proxy.dto.PageResponse;
import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import com.neqrofukk.medical_clinic_proxy.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PatientService service;

    @Test
    void getAllVisits_VisitsExist_Response200() throws Exception {
        // given
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        VisitDto visit = new VisitDto(1L, start, start.plusHours(1), 1L, 1L);
        when(service.findAllPatientVisits(1L)).thenReturn(Set.of(visit));

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/1/visits"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(1),
                        jsonPath("$[0].id").value(1),
                        jsonPath("$[0].startTime").value(formatDate(start)),
                        jsonPath("$[0].endTime").value(formatDate(start.plusHours(1))),
                        jsonPath("$[0].doctorId").value(1),
                        jsonPath("$[0].patientId").value(1)
                );
        verify(service).findAllPatientVisits(1L);
    }

    @Test
    void bookVisit_VisitExists_Response200() throws Exception {
        // given
        LocalDateTime date1 = LocalDateTime.now().plusDays(1);
        LocalDateTime date2 = LocalDateTime.now().plusDays(1).plusHours(1);
        VisitDto visit = new VisitDto(1L, date1, date2, 1L, 1L);
        when(service.bookVisit(1L, 1L)).thenReturn(visit);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.put("/patients/visits/1/patient/1"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1),
                        jsonPath("$.startTime").value(formatDate(date1)),
                        jsonPath("$.endTime").value(formatDate(date2)),
                        jsonPath("$.doctorId").value(1),
                        jsonPath("$.patientId").value(1)
                );
        verify(service).bookVisit(1L, 1L);
    }

    @Test
    void getAllDoctorAvailableVisits_VisitsExist_Response200() throws Exception {
        // given
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        VisitDto visit = new VisitDto(1L, start, start.plusHours(1), 1L, null);
        when(service.getAllDoctorAvailableVisits(1L)).thenReturn(Set.of(visit));

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/doctors/1/visits"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(1),
                        jsonPath("$[0].id").value(1),
                        jsonPath("$[0].startTime").value(formatDate(start)),
                        jsonPath("$[0].endTime").value(formatDate(start.plusHours(1))),
                        jsonPath("$[0].doctorId").value(1),
                        jsonPath("$[0].patientId").value(nullValue())
                );
        verify(service).getAllDoctorAvailableVisits(1L);
    }

    @Test
    void getVisits_VisitsExist_Response200() throws Exception {
        // given
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        VisitDto visit = new VisitDto(1L, start, start.plusHours(1), 1L, 1L);
        PageResponse<VisitDto> pageResponse = new PageResponse<>(List.of(visit), 0, 10, 1L, 1, true);
        when(service.getVisits(eq("eye doctor"), eq(start), eq(end), any())).thenReturn(pageResponse);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/visits")
                        .param("specialty", "eye doctor")
                        .param("startTime", formatDate(start))
                        .param("endTime", formatDate(end)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content.length()").value(1),
                        jsonPath("$.content[0].id").value(1),
                        jsonPath("$.content[0].startTime").value(formatDate(start)),
                        jsonPath("$.content[0].endTime").value(formatDate(start.plusHours(1))),
                        jsonPath("$.content[0].doctorId").value(1),
                        jsonPath("$.content[0].patientId").value(1),
                        jsonPath("$.page").value(0),
                        jsonPath("$.size").value(10),
                        jsonPath("$.totalElements").value(1),
                        jsonPath("$.totalPages").value(1),
                        jsonPath("$.last").value(true)
                );

        verify(service).getVisits("eye doctor", start, end, PageRequest.of(0, 10, Sort.by("startTime").ascending()));
    }

    @Test
    void getDoctors_DoctorsExist_DoctorsReturned() throws Exception {
        // given
        DoctorDto doctor = new DoctorDto(1L, "buziaczek67@serduszko.com", "Jan", "Grzebalski", "eye doctor");
        PageResponse<DoctorDto> pageResponse = new PageResponse<>(List.of(doctor), 0, 10, 1L, 1, true);
        when(service.getDoctors(eq("eye doctor"), any())).thenReturn(pageResponse);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/doctors")
                        .param("specialty", "eye doctor"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content.length()").value(1),
                        jsonPath("$.content[0].id").value(1),
                        jsonPath("$.content[0].email").value("buziaczek67@serduszko.com"),
                        jsonPath("$.content[0].firstName").value("Jan"),
                        jsonPath("$.content[0].lastName").value("Grzebalski"),
                        jsonPath("$.content[0].specialty").value("eye doctor"),
                        jsonPath("$.page").value(0),
                        jsonPath("$.size").value(10),
                        jsonPath("$.totalElements").value(1),
                        jsonPath("$.totalPages").value(1),
                        jsonPath("$.last").value(true)
                );

        verify(service).getDoctors("eye doctor", PageRequest.of(0, 10, Sort.by("lastName").ascending()));
    }

    private String formatDate(LocalDateTime date) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
    }
}