package com.honda.olympus.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.honda.olympus.exception.NotificationEmailException;
import com.honda.olympus.vo.EventVO;

@Service
public class EmailService {

	@Value("${spring.mail.host}")
	private String mailHost;

	@Value("${spring.mail.port}")
	private String mailPort;

	@Value("${spring.mail.username}")
	private String userName;

	@Value("${spring.mail.password}")
	private String mailPassword;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String auth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String strattls;

	@Value("${admin.mail.mailTo}")
	private String mailTo;

	@Value("${admin.mail.subject}")
	private String subject;
	
	@Value("${admin.mail.subject}")
	private String serviceName;
	
	@Value("${service.exception.message}")
	private String excptionMessage;
	
	@Value("${ervice.exception.message.secondpart}")
	private String exceptionMessageSecondPart;
	
	@Autowired
	LoggingService loggingService;

	public void sendEmail(String body) throws NotificationEmailException {
        EventVO event = null;
		
		try {

			Properties props = new Properties();
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.host", mailHost);
			props.put("mail.smtp.port", mailPort);
			props.put("mail.smtp.user", userName);
			props.put("mail.smtp.password", mailPassword);
			props.put("mail.debug", true);

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, mailPassword);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);
		
			System.out.println("Email sent succesfully");

		} catch (AddressException e) {
			event = new EventVO(serviceName,"0",excptionMessage + mailHost + exceptionMessageSecondPart + body,"");
			loggingService.sendLoggingEvent(event);
			throw new NotificationEmailException(
					excptionMessage + mailHost + " con el siguiente body: " + body);
		} catch (MessagingException e) {
			event = new EventVO(serviceName,"0",excptionMessage + mailHost + exceptionMessageSecondPart + body,"");
			loggingService.sendLoggingEvent(event);
			throw new NotificationEmailException(
					excptionMessage + mailHost + exceptionMessageSecondPart + body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
