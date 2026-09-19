package com.telemedicine.myhealth.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.telemedicine.myhealth.enums.Specialization;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private List<String> roles;

    private Specialization specialization;

    private String licenseNumber;

}
