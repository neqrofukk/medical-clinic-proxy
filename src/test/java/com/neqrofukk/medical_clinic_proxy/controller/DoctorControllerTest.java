package com.neqrofukk.medical_clinic_proxy.controller;

import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import com.neqrofukk.medical_clinic_proxy.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    DoctorService service;

    @Test
    void getAllDoctorVisits_VisitsExist_Response200() throws Exception {
        // given
        LocalDateTime date1 = LocalDateTime.now().plusDays(1);
        LocalDateTime date2 = date1.plusHours(2);
        VisitDto visit1 = new VisitDto(1L, date1, date1.plusHours(1), 1L, 1L);
        VisitDto visit2 = new VisitDto(2L, date2, date2.plusHours(1), 1L, 2L);
        when(service.getAllDoctorVisits(1L)).thenReturn(Set.of(visit1, visit2));

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/1/visits"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(2),
                        jsonPath("$[*].id", containsInAnyOrder(1, 2)),
                        jsonPath("$[*].startTime", containsInAnyOrder(formatDate(date1), formatDate(date2))),
                        jsonPath("$[*].endTime", containsInAnyOrder(formatDate(date1.plusHours(1)), formatDate(date2.plusHours(1)))),
                        jsonPath("$[*].patientId", containsInAnyOrder(1, 2))
                );
        verify(service).getAllDoctorVisits(1L);
    }

    @Test
    void cancelVisit_VisitExists_Response200() throws Exception {
        // given
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        VisitDto canceledVisit = new VisitDto(1L, date, date.plusHours(1), 1L, null);
        when(service.cancelVisit(1L)).thenReturn(canceledVisit);

        // when + then
        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/visits/1/patient"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1),
                        jsonPath("$.startTime").value(formatDate(date)),
                        jsonPath("$.endTime").value(formatDate(date.plusHours(1))),
                        jsonPath("$.doctorId").value(1),
                        jsonPath("$.patientId").value(nullValue())
                );
        verify(service).cancelVisit(1L);
    }

    private String formatDate(LocalDateTime date) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
    }
}