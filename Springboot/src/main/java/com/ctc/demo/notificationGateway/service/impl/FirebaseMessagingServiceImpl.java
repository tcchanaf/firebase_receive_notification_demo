package com.ctc.demo.notificationGateway.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ctc.demo.notificationGateway.constant.NotificationGatewayConstant;
import com.ctc.demo.notificationGateway.dto.NotificationRequestDTO;
import com.ctc.demo.notificationGateway.service.FirebaseMessagingService;
import com.ctc.demo.notificationGateway.utils.CTCLogger;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;



@Component
public class FirebaseMessagingServiceImpl implements FirebaseMessagingService{

	private FirebaseMessaging firebaseMessaging = null;

	private Firestore firestore = null;

	private final static String DATA_FIELD = "data";

	@PostConstruct
	public void initFirebase() throws IOException {

		FileInputStream serviceAccount = new FileInputStream(NotificationGatewayConstant.FIREBASE_CONFIG_PATH);

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp app = FirebaseApp.initializeApp(options);
		firebaseMessaging = FirebaseMessaging.getInstance(app);
		firestore = FirestoreClient.getFirestore(app);
	}

	@Override
	public BatchResponse sendMultiNotification(NotificationRequestDTO request) {
		CTCLogger.info("{0}: sendMultiNotification start, request:{1}", this.getClass(), request);

		boolean success = false;
		BatchResponse  result = null;
		MulticastMessage message = null;
		String[] deviceTokens = new String[] {};
		if (firebaseMessaging == null || firestore == null) {
			try {
				initFirebase();
			} catch (IOException e) {
				CTCLogger.error("Fail to init firebase caused by config file loading failure");
				e.printStackTrace();
			}
		}

		try {
			Notification notification = new Notification(request.getTitle(), request.getMessage());
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put(DATA_FIELD, request.getData());

			if (StringUtils.hasText(request.getDeviceTokens()))
				deviceTokens = request.getDeviceTokens().split("@@");

			message = MulticastMessage
					.builder()
					.addAllTokens(new ArrayList<String>(Arrays.asList(deviceTokens)))
					.setNotification(notification)
					.putAllData(dataMap)
					.build();

			result = firebaseMessaging.sendMulticast(message);
			success = true;

		} catch(FirebaseMessagingException e) {
			CTCLogger.info("Exception when sending message to Firebase caused by {0}}", e.getMessage());
		} catch (Exception e) {
			CTCLogger.info("Exception occurs caused by {0}}", e);
		}
		CTCLogger.info("{0}: sendMultiNotification end, success:{1}", getClass(), success);

		return result;
	}

}
