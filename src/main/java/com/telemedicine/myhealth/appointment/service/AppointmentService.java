package com.telemedicine.myhealth.appointment.service;

import com.telemedicine.myhealth.appointment.dto.AppointmentDTO;
import com.telemedicine.myhealth.res.Response;

import java.util.List;

public interface AppointmentService {
    Response<AppointmentDTO> bookAppointment(AppointmentDTO appointmentDTO);
    Response<List<AppointmentDTO>> getMyAppointments();
    Response<?> cancelAppointment(Long appointmentId);
    Response<?> completeAppointment(Long appointmentId);
}
