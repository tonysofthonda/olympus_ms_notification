package com.honda.olympus.service;

import com.honda.olympus.exception.NotificationEmailException;
import com.honda.olympus.vo.LogEventVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service
public class EmailService {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private String mailPort;

    @Value("${admin.mail.mailTo}")
    private String mailTo;

    @Value("${admin.mail.subject}")
    private String subject;

    @Value("${service.name}")
    private String serviceName;

    @Value("${service.exception.message}")
    private String excptionMessage;

    @Value("${service.exception.message.secondpart}")
    private String exceptionMessageSecondPart;

    @Autowired
    LogEventService loggingService;

    @Value("${service.success.message}")
    private String successMesage;

    @Resource(mappedName = "java:jboss/mail/honda")
    private Session mailSession;

    public void sendEmail(String body, String fileName, String source) throws NotificationEmailException {
        LogEventVO event = null;

        try {

            Message message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
            message.setSubject(subject + "(" + source + ")");
            message.setText(body);

            Transport.send(message);

            log.info("Notification:: Email SENT succesfully");

            event = new LogEventVO(serviceName, 1L, successMesage, fileName);
            loggingService.sendLogEvent(event);

        } catch (MessagingException e) {

            event = new LogEventVO(serviceName, 0L, excptionMessage + " " + mailHost + " " + exceptionMessageSecondPart + body, fileName);
            loggingService.sendLogEvent(event);
            throw new NotificationEmailException(
                    excptionMessage + " " + mailHost + " " + exceptionMessageSecondPart + body);
        } catch (Exception e) {
            log.debug("Exception caused by: {}", e.getLocalizedMessage());
        }
    }

}
