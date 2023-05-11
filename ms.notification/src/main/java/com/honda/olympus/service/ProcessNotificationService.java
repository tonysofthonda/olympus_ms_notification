package com.honda.olympus.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honda.olympus.exception.NotificationEmailException;
import com.honda.olympus.vo.EventVO;
import com.honda.olympus.vo.ResponseVO;

@Service
public class ProcessNotificationService {

	@Autowired
	EmailService emailService;

	public static final String RESPONSE_MESSAGE = "Notification succsefully sent";
	public static final String RESPONSE_ERROR_MESSAGE = "Notification failure";

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

			emailService.sendEmail(notificationBody.toString());

			return new ResponseVO(RESPONSE_MESSAGE,null);

	}

}
