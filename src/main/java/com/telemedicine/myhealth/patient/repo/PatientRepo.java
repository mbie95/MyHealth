package com.telemedicine.myhealth.patient.repo;

import com.telemedicine.myhealth.patient.entity.Patient;
import com.telemedicine.myhealth.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser(User user);
}
