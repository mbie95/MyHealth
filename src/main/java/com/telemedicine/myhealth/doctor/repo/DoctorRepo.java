package com.telemedicine.myhealth.doctor.repo;

import com.telemedicine.myhealth.doctor.entity.Doctor;
import com.telemedicine.myhealth.enums.Specialization;
import com.telemedicine.myhealth.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(User user);
    List<Doctor> findBySpecialization(Specialization specialization);
}
