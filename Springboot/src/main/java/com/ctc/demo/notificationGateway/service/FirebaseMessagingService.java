package com.ctc.demo.notificationGateway.service;

import com.ctc.demo.notificationGateway.dto.NotificationRequestDTO;
import com.google.firebase.messaging.BatchResponse;

public interface FirebaseMessagingService {

	public BatchResponse sendMultiNotification(NotificationRequestDTO request);

}
