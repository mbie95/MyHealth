package com.telemedicine.myhealth.doctor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.telemedicine.myhealth.enums.Specialization;
import com.telemedicine.myhealth.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Specialization specialization;

    private String licenseNumber;

    private UserDTO user;

}
