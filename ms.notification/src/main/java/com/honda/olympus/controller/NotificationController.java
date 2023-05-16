package com.honda.olympus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honda.olympus.exception.NotificationEmailException;
import com.honda.olympus.service.ProcessNotificationService;
import com.honda.olympus.vo.EventVO;
import com.honda.olympus.vo.ResponseVO;

@RestController
@Validated
public class NotificationController {

	@Autowired
	ProcessNotificationService notificationService;

	@RequestMapping("/")
	public String homeController() {

		return "Honda Olympus Notification Home V1";

	}
	
	@Value("${service.success.message}")
	private String responseMessage;

	@PostMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> sendNotification(@Valid @RequestBody EventVO notififcationsDetails)
			throws NotificationEmailException {

		System.out.println(notififcationsDetails.toString());
		notificationService.processNotification(notififcationsDetails);

		return new ResponseEntity<ResponseVO>(new ResponseVO(responseMessage, null), HttpStatus.OK);

	}
}
