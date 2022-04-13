package com.ctc.demo.notificationGateway.dto;

import lombok.Data;

@Data
public class NotificationRequestDTO {

	  private String deviceTokens;

	  private String title;

	  private String message;

	  private String data;

}
