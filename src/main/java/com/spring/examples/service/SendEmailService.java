package com.spring.examples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmailService {

	@Autowired
	public JavaMailSender emailSender;

	
	public void sendSimpleMessage(String to, String subject, String text) throws InterruptedException {
		try{
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			emailSender.send(message);
		}catch(Exception e){
		}
	}
}
