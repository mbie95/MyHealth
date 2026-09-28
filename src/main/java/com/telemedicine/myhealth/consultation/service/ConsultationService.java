package com.telemedicine.myhealth.consultation.service;

import com.telemedicine.myhealth.consultation.dto.ConsultationDTO;
import com.telemedicine.myhealth.res.Response;

import java.util.List;

public interface ConsultationService {
    Response<ConsultationDTO> createConsultation(ConsultationDTO consultationDTO);
    Response<ConsultationDTO> getConsultationByAppointmentId(Long appointmentId);
    Response<List<ConsultationDTO>> getConsultationHistoryForPatient(Long patientId);
}
