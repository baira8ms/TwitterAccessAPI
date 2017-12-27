package com.spring.examples;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class ClientConfiguration {
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		return new JavaMailSenderImpl();
	}
}
