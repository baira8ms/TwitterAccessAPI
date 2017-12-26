package com.spring.examples;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//@Component
public class ClientConfiguration {
//	String url = "http://localhost:8080";
//	
//	@Bean
//	public StudentFeignClient studentFeignClient(){
//		StudentFeignClient studentFeignClient = Feign
//				.builder()
//				.encoder(new JacksonEncoder())
//		        .decoder(new JacksonDecoder())
//		        .target(StudentFeignClient.class, url);
//		return studentFeignClient;	
//	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		return new JavaMailSenderImpl();
	}
}
