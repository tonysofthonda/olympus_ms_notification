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

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
public class NotificationController {

	@Autowired
	ProcessNotificationService notificationService;
	
	@Value("${service.name}")
	private String serviceName;
	
	@Value("${service.success.message}")
	private String responseMessage;

	@Operation(summary = "Send a notification")
	@PostMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> sendNotification(@Valid @RequestBody EventVO notififcationsDetails)
			throws NotificationEmailException {

		log.debug("Event: {}",notififcationsDetails.toString());
		notificationService.processNotification(notififcationsDetails);

		return new ResponseEntity<>(new ResponseVO(notififcationsDetails.getSource(),1L,responseMessage, ""), HttpStatus.OK);

	}
}
