package com.neqrofukk.medical_clinic_proxy.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "medicalClinicClient",
        url = "http://localhost:8081",
        configuration = MedicalClinicClientConfig.class
)
public interface MedicalClinicClient {

    // Jako pacjent:
    // Możliwość zapisania się na wizytę
    // Możliwość sprawdzenia wszystkich dostępnych wizyt dla danego doktora
    // Sprawdzenia wszystkich terminów dla danego przedzialu czasowego dla danej specjalizacji i bez specjalizacji
    // Sprawdzić wszystkich doktórów z danej specjalizacji


    // jako doctor:
    // Możliwość zobaczenia wszystkich swoich wizyt -> przeszlych terazniejszych wszystkich
    // Chcialbym miec mozliwosc zrezygnowania z wizyty -> tzn. ktos jest zapisany ale wtedy nie moge wiec chce taka wizyte moc odwolac
}
