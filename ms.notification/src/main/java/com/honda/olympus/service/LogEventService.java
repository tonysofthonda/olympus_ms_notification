package com.honda.olympus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.honda.olympus.vo.EventVO;


@Service
public class LogEventService {
	@Value("${logEvent.service.url}")
	private String notificationURI;

	public void sendLogEvent(EventVO message) {
		
		try {
			
			System.out.println("Creting logEvent...");
			System.out.println(message.toString());
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			RestTemplate restTemplate = new RestTemplate();
			
			HttpEntity<EventVO> requestEntity = new HttpEntity<>(message, headers);

			ResponseEntity<EventVO> responseEntity = restTemplate.postForEntity(notificationURI, requestEntity, EventVO.class);

			System.out.println("Status Code: " + responseEntity.getStatusCode());		
			System.out.println("Message: " + responseEntity.getBody().getMsg());
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		
		
		
	}
	
}