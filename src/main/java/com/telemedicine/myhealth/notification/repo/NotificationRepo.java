package com.telemedicine.myhealth.notification.repo;

import com.telemedicine.myhealth.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
}
