package com.ctc.demo.notificationGateway;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.StringUtils;


@EntityScan({"com.ctc.demo.notificationGateway.*"})
@SpringBootApplication
public class NotificationGatewaySpringBootApplication {

	public static void main(String[] args) throws URISyntaxException {

		String baseDir = System.getProperty("base.dir");

		if (!StringUtils.hasText(baseDir)) {
			URL location = NotificationGatewaySpringBootApplication.class.getProtectionDomain().getCodeSource().getLocation();
			String protocol = location.getProtocol();
			if ("jar".equalsIgnoreCase(protocol)) {
				ApplicationHome home = new ApplicationHome(NotificationGatewaySpringBootApplication.class);
				baseDir = home.getDir().getPath();
			} else {
				baseDir = new File(location.toURI()).getParent();
			}

			System.getProperties().put("base.dir", baseDir);
		}

		SpringApplication.run(NotificationGatewaySpringBootApplication.class, args);

	}

}
