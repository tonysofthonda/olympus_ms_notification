package com.honda.olympus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.honda.olympus.vo.LogEventVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogEventService {
	@Value("${logEvent.service.url}")
	private String notificationURI;

	public void sendLogEvent(LogEventVO message) {
		
		try {
			
			log.debug("Creting logEvent...");
			log.debug("MEssage: {}",message.toString());
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			RestTemplate restTemplate = new RestTemplate();
			
			HttpEntity<LogEventVO> requestEntity = new HttpEntity<>(message, headers);

			ResponseEntity<String> responseEntity = restTemplate.postForEntity(notificationURI, requestEntity, String.class);

			log.debug("Status Code: {}",responseEntity.getStatusCode());		
			log.debug("Message: {}",responseEntity.getBody());
			
		} catch (Exception e) {
			log.info("Notification error calling logEvent service due to: {}",e.getMessage());	
		}
		
	}
	
}