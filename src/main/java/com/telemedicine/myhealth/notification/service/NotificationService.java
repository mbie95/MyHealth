package com.telemedicine.myhealth.notification.service;

import com.telemedicine.myhealth.notification.dto.NotificationDTO;
import com.telemedicine.myhealth.user.entity.User;

public interface NotificationService {
    void sendEmail(NotificationDTO notificationDTO, User user);
}
