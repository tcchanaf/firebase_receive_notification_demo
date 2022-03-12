package com.ctc.demo.notificationGateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.demo.notificationGateway.api.PushNotificationApi;
import com.ctc.demo.notificationGateway.model.BaseResult;
import com.ctc.demo.notificationGateway.model.NotificationRequest;
import com.ctc.demo.notificationGateway.service.FirebaseMessagingService;


@RestController
public class NotificationController implements PushNotificationApi{
//	private String token = "ftaD82QFSQePQQlQD9IEEM:APA91bGCAeHJ2ECdIox01KXoOU93_b2pY78ZqmDlOV5k2A9S5q7I-6BrULY3-3QGO_Jdx1ZhjoA0mc5zZqtsOUH3fCD8pBtrg9xDhKiWtsOP82lQfQNRb_YHxaEQ93qfM66h9VtnJ4Ty";

	@Autowired
	private FirebaseMessagingService firebaseMessagingService;

	@Override
	public ResponseEntity<BaseResult> pushNotificationPOST(NotificationRequest body){
		BaseResult resp = new BaseResult();
		try {
//			ResultDTO result = firebaseBizDelegate.pushFirebaseNotification(convert(body));
			firebaseMessagingService.sendMultiNotification(body);

//			this.handleResult(resp, result);
		} catch (Throwable t) {
//			this.handleError(resp, t);
		}

		return new ResponseEntity<BaseResult>(resp, HttpStatus.OK);
	}


//	protected PushNotificationRequest convert(PushNotificationCriteria src) {
//		PushNotificationRequest dto = new PushNotificationRequest();
//		dto.ecGatewayDeviceId(src.getEcGatewayDeviceId())
//		.deviceToken(src.getDeviceToken())
//		.title(src.getTitle())
//		.message(src.getMessage())
//		.dataType(src.getDataType())
//		.data(src.getData());
//
//		return dto;
//	}
}
