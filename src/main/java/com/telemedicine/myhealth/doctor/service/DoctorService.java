package com.telemedicine.myhealth.doctor.service;

import com.telemedicine.myhealth.doctor.dto.DoctorDTO;
import com.telemedicine.myhealth.enums.Specialization;
import com.telemedicine.myhealth.res.Response;

import java.util.List;

public interface DoctorService {
    Response<DoctorDTO> getDoctorProfile();
    Response<?> updateDoctorProfile(DoctorDTO doctorDTO);
    Response<List<DoctorDTO>> getAllDoctors();
    Response<DoctorDTO> getDoctorById(Long doctorId);
    Response<List<DoctorDTO>> searchDoctorsBySpecialization(Specialization specialization);
    Response<List<Specialization>> getAllSpecializationEnums();
}
