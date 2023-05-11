package com.honda.olympus.service;

import org.springframework.stereotype.Service;

import com.honda.olympus.vo.EventVO;

@Service
public class LoggingService {

	public void sendLoggingEvent(EventVO event) {
		System.out.println("Calling loggin service...");
		System.out.println(event.toString());
	}
}
