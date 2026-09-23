package com.telemedicine.myhealth.user.service;

import com.telemedicine.myhealth.doctor.entity.Doctor;
import com.telemedicine.myhealth.doctor.repo.DoctorRepo;
import com.telemedicine.myhealth.exception.BadRequestException;
import com.telemedicine.myhealth.exception.NotFoundException;
import com.telemedicine.myhealth.notification.dto.NotificationDTO;
import com.telemedicine.myhealth.notification.service.NotificationService;
import com.telemedicine.myhealth.patient.entity.Patient;
import com.telemedicine.myhealth.patient.repo.PatientRepo;
import com.telemedicine.myhealth.res.Response;
import com.telemedicine.myhealth.role.entity.Role;
import com.telemedicine.myhealth.role.repo.RoleRepo;
import com.telemedicine.myhealth.security.JwtService;
import com.telemedicine.myhealth.user.dto.LoginRequest;
import com.telemedicine.myhealth.user.dto.LoginResponse;
import com.telemedicine.myhealth.user.dto.RegistrationRequest;
import com.telemedicine.myhealth.user.dto.ResetPasswordRequest;
import com.telemedicine.myhealth.user.entity.User;
import com.telemedicine.myhealth.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;

    private final JwtService tokenService;
    private final NotificationService notificationService;

    private final PasswordEncoder passwordEncoder;

    @Value("${login.link}")
    private String loginLink;

    @Override
    public Response<String> register(RegistrationRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("User with email already exists");
        }

        List<String> requestedRoleNames = (request.getRoles() != null && !request.getRoles().isEmpty())
                ? request.getRoles().stream().map(String::toUpperCase).toList()
                : List.of("PATIENT");

        boolean isDoctor = requestedRoleNames.contains("DOCTOR");

        if (isDoctor && (request.getLicenseNumber() == null || request.getLicenseNumber().isBlank())) {
            throw new BadRequestException("License number required to register a doctor.");
        }

        List<Role> roles = requestedRoleNames.stream()
                .map(roleRepo::findByName)
                .flatMap(Optional::stream)
                .toList();

        if (roles.isEmpty()) {
            throw new NotFoundException("Registration failed: Requested roles were not found in the database.");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .roles(roles)
                .build();

        User savedUser = userRepo.save(newUser);
        log.info("New user registered: {} with {} roles.", savedUser.getEmail(), roles.size());

        for (Role role : roles) {
            String roleName = role.getName();

            switch (roleName) {
                case "PATIENT":
                    createPatientProfile(savedUser);
                    log.info("Patient profile created: {}", savedUser.getEmail());
                    break;

                case "DOCTOR":
                    createDoctorProfile(request, savedUser);
                    log.info("Doctor profile created: {}", savedUser.getEmail());
                    break;

                case "ADMIN":
                    log.info("Admin role assigned to user: {}", savedUser.getEmail());
                    break;

                default:
                    log.warn("Assigned role '{}' has no corresponding profile creation logic.", roleName);
                    break;
            }
        }

        sendRegistrationEmail(request, savedUser);

        return Response.<String>builder()
                .statusCode(200)
                .message("Registration successful. A welcome email has been sent to you.")
                .data(savedUser.getEmail())
                .build();
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Password doesn't match");
        }

        String token = tokenService.generateToken(user.getEmail());

        LoginResponse loginResponse = LoginResponse.builder()
                .roles(user.getRoles().stream().map(Role::getName).toList())
                .token(token)
                .build();

        return Response.<LoginResponse>builder()
                .statusCode(200)
                .message("Login Successful")
                .data(loginResponse)
                .build();
    }

    @Override
    public Response<?> forgetPassword(String email) {
        return null;
    }

    @Override
    public Response<?> updatePasswordViaResetCode(ResetPasswordRequest resetPasswordRequest) {
        return null;
    }

    private void createPatientProfile(User user) {
        Patient patient = Patient.builder()
                .user(user)
                .build();

        patientRepo.save(patient);
        log.info("Patient profile created");
    }

    private void createDoctorProfile(RegistrationRequest request, User user) {
        Doctor doctor = Doctor.builder()
                .specialization(request.getSpecialization())
                .licenseNumber(request.getLicenseNumber())
                .user(user)
                .build();

        doctorRepo.save(doctor);
        log.info("Doctor profile created");
    }

    private void sendRegistrationEmail(RegistrationRequest request, User user) {
        log.info("Trying to send Email Out");

        NotificationDTO welcomeEmail = NotificationDTO.builder()
                .recipient(user.getEmail())
                .subject("Welcome to MyHealth!")
                .templateName("welcome")
                .body("Thank you for registering. Your account is ready.")
                .templateVariables(Map.of(
                        "name", request.getName(),
                        "loginLink", loginLink
                ))
                .build();

        notificationService.sendEmail(welcomeEmail, user);
    }
}
