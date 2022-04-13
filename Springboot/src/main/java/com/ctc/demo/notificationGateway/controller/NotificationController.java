package com.ctc.demo.notificationGateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.demo.notificationGateway.api.PushNotificationApi;
import com.ctc.demo.notificationGateway.dto.NotificationRequestDTO;
import com.ctc.demo.notificationGateway.model.BaseResult;
import com.ctc.demo.notificationGateway.model.NotificationRequest;
import com.ctc.demo.notificationGateway.service.FirebaseMessagingService;


@RestController
public class NotificationController implements PushNotificationApi{

	@Autowired
	private FirebaseMessagingService firebaseMessagingService;

	@Override
	public ResponseEntity<BaseResult> pushNotificationPOST(NotificationRequest body){
		BaseResult resp = new BaseResult();
		try {
			firebaseMessagingService.sendMultiNotification(convert(body));
			resp.setStatus("Success");
		} catch (Throwable t) {
			t.printStackTrace();
			resp.setStatus("Error");

			return new ResponseEntity<BaseResult>(resp, HttpStatus.OK);
		}

		return new ResponseEntity<BaseResult>(resp, HttpStatus.OK);
	}


	private NotificationRequestDTO convert(NotificationRequest src) {
		NotificationRequestDTO dto = new NotificationRequestDTO();
		dto.setDeviceTokens(src.getDeviceTokens());
		dto.setTitle(src.getTitle());
		dto.setMessage(src.getMessage());
		dto.setData(src.getData());

		return dto;
	}
}
