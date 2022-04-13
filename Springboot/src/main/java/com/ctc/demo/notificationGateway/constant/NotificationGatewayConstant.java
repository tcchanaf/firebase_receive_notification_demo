package com.ctc.demo.notificationGateway.constant;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;



@PropertySources({
    @PropertySource("classpath:firebase_config.properties"),
})
@Component
public class NotificationGatewayConstant {
	@Value("${app.firebase-configuration-file}")
	private String firebaseConfigPath;
	public static String FIREBASE_CONFIG_PATH;

	@PostConstruct
	public void init(){
		FIREBASE_CONFIG_PATH = firebaseConfigPath;

	}

}
