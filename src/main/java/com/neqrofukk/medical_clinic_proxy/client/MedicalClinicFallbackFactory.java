package com.neqrofukk.medical_clinic_proxy.client;

import com.neqrofukk.medical_clinic_proxy.dto.DoctorDto;
import com.neqrofukk.medical_clinic_proxy.dto.PageResponse;
import com.neqrofukk.medical_clinic_proxy.dto.VisitDto;
import com.neqrofukk.medical_clinic_proxy.exception.MedicalClinicProxyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@Slf4j
public class MedicalClinicFallbackFactory implements FallbackFactory<MedicalClinicClient> {

    @Override
    public MedicalClinicClient create(Throwable cause) {
        log.error("MedicalClinicClient exception occured, returning a fallback. Error: ", cause);
        return new MedicalClinicClient() {
            @Override
            public Set<VisitDto> findAllPatientVisits(Long patientId) {
                return Set.of();
            }

            @Override
            public VisitDto bookVisit(Long visitId, Long patientId) {
                throw new MedicalClinicProxyException("Medical clinic service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
            }

            @Override
            public Set<VisitDto> findAllDoctorVisits(Long doctorId, Boolean available) {
                return Set.of();
            }

            @Override
            public PageResponse<VisitDto> getVisits(String specialty, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
                return PageResponse.empty();
            }

            @Override
            public PageResponse<DoctorDto> getDoctors(String specialty, Pageable pageable) {
                return PageResponse.empty();
            }

            @Override
            public VisitDto cancelVisit(Long visitId) {
                throw new MedicalClinicProxyException("Medical clinic service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
            }
        };
    }

}
