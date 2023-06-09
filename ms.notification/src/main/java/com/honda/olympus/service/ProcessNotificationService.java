package com.honda.olympus.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.honda.olympus.exception.NotificationEmailException;
import com.honda.olympus.vo.EventVO;
import com.honda.olympus.vo.ResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProcessNotificationService {

	@Autowired
	EmailService emailService;

	@Value("${service.success.message}")
	private String responseMessage;
	
	public ResponseVO processNotification(EventVO notificationDetails) throws NotificationEmailException {

			final Date currentDate = new Date();
			final StringBuilder notificationBody = new StringBuilder();

			notificationBody.append("Fecha: ");
			notificationBody.append(currentDate);

			notificationBody.append(", Origen: ");
			notificationBody.append(notificationDetails.getSource());

			notificationBody.append(", Estatus: ");
			notificationBody.append(notificationDetails.getStatus());

			notificationBody.append(", Mensaje: ");
			notificationBody.append(notificationDetails.getMsg());

			notificationBody.append(", Archivo: ");
			notificationBody.append(notificationDetails.getFile());

			String subjectDetail = notificationDetails.getSource()+"-Status: "+notificationDetails.getStatus();
			log.debug("Subject: {}",subjectDetail);
			
			emailService.sendEmail(notificationBody.toString(),notificationDetails.getFile(),subjectDetail);

			return new ResponseVO(notificationDetails.getSource(),1L,responseMessage,"");

	}

}
