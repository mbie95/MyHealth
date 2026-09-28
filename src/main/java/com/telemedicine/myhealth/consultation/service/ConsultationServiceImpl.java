package com.telemedicine.myhealth.consultation.service;

import com.telemedicine.myhealth.appointment.entity.Appointment;
import com.telemedicine.myhealth.appointment.repo.AppointmentRepo;
import com.telemedicine.myhealth.consultation.dto.ConsultationDTO;
import com.telemedicine.myhealth.consultation.entity.Consultation;
import com.telemedicine.myhealth.consultation.repo.ConsultationRepo;
import com.telemedicine.myhealth.enums.AppointmentStatus;
import com.telemedicine.myhealth.exception.BadRequestException;
import com.telemedicine.myhealth.exception.NotFoundException;
import com.telemedicine.myhealth.patient.entity.Patient;
import com.telemedicine.myhealth.patient.repo.PatientRepo;
import com.telemedicine.myhealth.res.Response;
import com.telemedicine.myhealth.user.entity.User;
import com.telemedicine.myhealth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepo consultationRepo;
    private final AppointmentRepo appointmentRepo;
    private final PatientRepo patientRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Response<ConsultationDTO> createConsultation(ConsultationDTO consultationDTO) {
        User user = userService.getCurrentUser();
        Long appointmentId = consultationDTO.getAppointmentId();

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Appointment not found."));

        if (!appointment.getDoctor().getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You are not authorized to create notes for this consultation.");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepo.save(appointment);

        if (consultationRepo.findByAppointmentId(appointmentId).isPresent()) {
            throw new BadRequestException("Consultation notes already exist for this appointment.");
        }

        Consultation consultation = Consultation.builder()
                .consultationDate(LocalDateTime.now())
                .subjectiveNotes(consultationDTO.getSubjectiveNotes())
                .objectiveFindings(consultationDTO.getObjectiveFindings())
                .assessment(consultationDTO.getAssessment())
                .plan(consultationDTO.getPlan())
                .appointment(appointment)
                .build();

        consultationRepo.save(consultation);

        return Response.<ConsultationDTO>builder()
                .statusCode(200)
                .message("Consultation notes saved successfully.")
                .build();
    }

    @Override
    public Response<ConsultationDTO> getConsultationByAppointmentId(Long appointmentId) {
        Consultation consultation = consultationRepo.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new NotFoundException("Consultation notes not found for appointment ID: " + appointmentId));

        return Response.<ConsultationDTO>builder()
                .statusCode(200)
                .message("Consultation notes retrieved successfully.")
                .data(modelMapper.map(consultation, ConsultationDTO.class))
                .build();
    }

    @Override
    public Response<List<ConsultationDTO>> getConsultationHistoryForPatient(Long patientId) {
        User user = userService.getCurrentUser();

        if (patientId == null) {
            Patient currentPatient = patientRepo.findByUser(user)
                    .orElseThrow(() -> new BadRequestException("Patient profile not found for the current user"));
            patientId = currentPatient.getId();
        }

        patientRepo.findById(patientId)
                .orElseThrow(() -> new NotFoundException("Patient not found "));

        List<Consultation> history = consultationRepo.findByAppointmentPatientIdOrderByConsultationDateDesc(patientId);

        if (history.isEmpty()) {
            return Response.<List<ConsultationDTO>>builder()
                    .statusCode(200)
                    .message("No consultation history found for this patient.")
                    .data(List.of())
                    .build();
        }

        List<ConsultationDTO> historyDTOs = history.stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDTO.class))
                .toList();

        return Response.<List<ConsultationDTO>>builder()
                .statusCode(200)
                .message("Consultation history retrieved successfully.")
                .data(historyDTOs)
                .build();
    }

}
