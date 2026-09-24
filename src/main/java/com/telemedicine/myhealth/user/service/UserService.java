package com.telemedicine.myhealth.user.service;

import com.telemedicine.myhealth.res.Response;
import com.telemedicine.myhealth.user.dto.UpdatePasswordRequest;
import com.telemedicine.myhealth.user.dto.UserDTO;
import com.telemedicine.myhealth.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User getCurrentUser();
    Response<UserDTO> getMyUserDetails();
    Response<UserDTO> getUserById(Long userId);
    Response<List<UserDTO>> getAllUsers();
    Response<?> updatePassword(UpdatePasswordRequest updatePasswordRequest);
    Response<?> uploadProfilePicture(MultipartFile file);
}
