package com.ctc.demo.notificationGateway.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ctc.demo.notificationGateway.model.NotificationRequest;
import com.ctc.demo.notificationGateway.service.FirebaseMessagingService;
import com.ctc.demo.notificationGateway.utils.GatewayLogger;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;



@Component
public class FirebaseMessagingServiceImpl implements FirebaseMessagingService{
	@Value("${app.firebase-configuration-file}")
	private String firebaseConfigPath;


	//	@Value("#{${app.notifications.defaults}}")
	//	private Map<String, String> defaults;

	private FirebaseMessaging firebaseMessaging;

	private Firestore firestore;

	private final static String DATA_FIELD = "data";
	private final static String DATA_TYPE_FIELD = "dataType";


	@PostConstruct
	public void init() throws IOException {
		//			firebaseConfigPath = CommonUtils.expandEnvVars(firebaseConfigPath);

		FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp app = FirebaseApp.initializeApp(options);
		firebaseMessaging = FirebaseMessaging.getInstance(app);
		firestore = FirestoreClient.getFirestore(app);

	}

	//	@Override
	//	public WriteResult putDeviceFirestore(
	//			String deviceId,
	//			String deviceToken,
	//			String user,
	//			String deviceInfo) throws InterruptedException, ExecutionException{
	//		Device deviceRecord = new Device(deviceId, deviceToken, user, deviceInfo);
	//		ApiFuture<WriteResult> future = firestore.collection("users").document(deviceId).set(deviceRecord);
	//
	//		return future.get();
	//	}
	//
	//	@Override
	//	public Device getDeviceFirestoreByDeviceId(String deviceId) throws InterruptedException, ExecutionException {
	//		DocumentReference docRef = firestore.collection("users").document(deviceId);
	//		ApiFuture<DocumentSnapshot> future = docRef.get();
	//		DocumentSnapshot document = future.get();
	//		Device deviceRecord = null;
	//		if (document.exists()) {
	//			// convert document to POJO
	//			deviceRecord = document.toObject(Device.class);
	//		}
	//
	//		return deviceRecord;
	//	}


	//	@Override
	//	public String sendNotification(PushNotificationRequest request) throws ServiceException {
	//		SystemLogger.debugMethod(getClass(), "sendNotification", true,
	//				new String[] {"PushNotificationRequest"}, request);
	//
	//		Notification notification = new Notification(request.getTitle(), request.getMessage());
	//		Map<String, String> dataMap = new HashMap<String, String>();
	//		dataMap.put(DATA_FIELD, request.getData());
	//		dataMap.put(DATA_TYPE_FIELD, request.getDataType());
	//
	//		String result = null;
	//		Message message = null;
	//		try {
	//			message = Message
	//					.builder()
	//					.setToken(request.getDeviceToken())
	//					.setNotification(notification)
	//					.putAllData(dataMap)
	//					.build();
	//
	//			result = firebaseMessaging.send(message);
	//		} catch(FirebaseMessagingException e) {
	//			throw new ServiceException(MessageFormat.format("Exception found in sending message to Firebase caused by [{0}}]", e.getMessage()));
	//		}
	//		SystemLogger.debugMethod(getClass(), "sendNotification", false, new String[] {"message"}, message);
	//		return result;
	//	}

	@Override
	public BatchResponse sendMultiNotification(NotificationRequest request) {
		GatewayLogger.info("{0}: sendMultiNotification start, request:{1}", this.getClass(), request);

		boolean success = false;
		BatchResponse  result = null;
		MulticastMessage message = null;
		String[] deviceTokens = new String[] {};

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

			//		} catch(FirebaseMessagingException e) {
			//			GatewayLogger.info("Exception when sending message to Firebase caused by {0}}", e.getMessage());
		} catch (Exception e) {
			GatewayLogger.info("Exception occurs caused by {0}}", e);
		}
		GatewayLogger.info("{0}: sendMultiNotification end, success:{1}", getClass(), success);

		return result;
	}

}
