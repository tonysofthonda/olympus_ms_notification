package com.honda.olympus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.olympus.service.ProcessNotificationService;
import com.honda.olympus.vo.EventVO;
import com.honda.olympus.vo.ResponseVO;

@RestController
public class NotificationController {

	@Autowired
	ProcessNotificationService notificationService;

	@RequestMapping("/")
	public String homeController() {

		return "Honda Olympus Notification Home";

	}

	@PostMapping(value = "/olympus/v1/notification", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> sendNotification(@RequestBody EventVO notififcationsDetails) throws Exception{
		
			System.out.println(notififcationsDetails.toString());
			notificationService.processNotification(notififcationsDetails);
			
			return new ResponseEntity<ResponseVO>(new ResponseVO("Notification sent successsfully",null),HttpStatus.OK);
			

	}

}
