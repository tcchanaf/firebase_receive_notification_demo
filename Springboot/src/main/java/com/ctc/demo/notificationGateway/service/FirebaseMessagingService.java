package com.ctc.demo.notificationGateway.service;

import com.ctc.demo.notificationGateway.model.NotificationRequest;
import com.google.firebase.messaging.BatchResponse;

public interface FirebaseMessagingService {

	BatchResponse sendMultiNotification(NotificationRequest request);

}
