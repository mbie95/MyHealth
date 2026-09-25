package com.telemedicine.myhealth.patient.service;

import com.telemedicine.myhealth.enums.BloodGroup;
import com.telemedicine.myhealth.enums.Genotype;
import com.telemedicine.myhealth.patient.dto.PatientDTO;
import com.telemedicine.myhealth.res.Response;

import java.util.List;

public interface PatientService {
    Response<PatientDTO> getPatientProfile();
    Response<?> updatePatientProfile(PatientDTO patientDTO);
    Response<PatientDTO> getPatientById(Long patientId);
    Response<List<BloodGroup>> getAllBloodGroupEnums();
    Response<List<Genotype>> getAllGenotypeEnums();
}
