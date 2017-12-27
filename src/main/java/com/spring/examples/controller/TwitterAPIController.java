package com.spring.examples.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.examples.service.SendEmailService;
import com.spring.examples.service.TwitterAPIService;

import twitter4j.Status;

@RestController
@RequestMapping(value = "/twitter")
public class TwitterAPIController {

	private static final Logger logger = Logger.getLogger(TwitterAPIController.class);

	private TwitterAPIService twitterAPIService;
	public static int count;
	public static List<Long> listOfExistingId;
	public static int currentDay;

	@Autowired
	private SendEmailService emailService;

	@Autowired
	public void setTwitterAPIService(TwitterAPIService twitterAPIService) {
		this.twitterAPIService = twitterAPIService;
	}

	@Value("${emailId}")
	public String emailId;

	@Value("${emailSubject}")
	public String emailSubject;

	public TwitterAPIController(){
		listOfExistingId = new ArrayList<Long>();
	}

	@Scheduled(fixedDelay = 65000)
	@GetMapping(value = "/tweets")
	public void getAllTweets() throws Exception {
		List<Status> list = twitterAPIService.getMcCaffeTweets();
		logger.info("Status List:" + list.size());

		if (currentDay != new Date().getDate()) {
			listOfExistingId = new ArrayList<Long>();
			currentDay = new Date().getDate();
			count = 0;
		}

		logger.info("current Day:" + currentDay);
		logger.info("Existing list of Id list size:" + listOfExistingId.size());

		if (list.size() > count) {
			count = list.size();

			list.forEach(listItem -> {
				if (!listOfExistingId.contains(listItem.getId())) {
					logger.info("New Tweet found and forwarding to mailing list:" + listItem.getId());
					listOfExistingId.add(listItem.getId());
					try {
						logger.info(emailId);
						logger.info(emailSubject);
						emailService.sendSimpleMessage(emailId, emailSubject, listItem.getText());
					} catch (Exception e) {
					}
				}
			});
			logger.info("Email sent successfully.");
		} else {
			logger.info("No Twitter updates so no need of Email.");
		}
	}
}
