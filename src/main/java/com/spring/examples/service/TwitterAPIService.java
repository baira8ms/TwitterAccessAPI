package com.spring.examples.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Service
public class TwitterAPIService {
	Logger logger = Logger.getLogger(TwitterAPIService.class);

	String consumerKeyStr = "xxx";
	String consumerSecretStr = "xxx";
	String accessTokenStr = "xxxx";
	String accessTokenSecretStr = "xxxxO";

	@Value("${twitterUserId}")
	public String twitterUserId;
	
	private Twitter twitter;

	public TwitterAPIService() {
		twitter = new TwitterFactory().getInstance();

		twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
		AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr);
		twitter.setOAuthAccessToken(accessToken);
	}

	public List<Status> getMcCaffeTweets() {
		logger.info("Retreiving Twitter feeds:");
		List<Status> statusList = new ArrayList<Status>();

		try {
			Paging paging = new Paging(1, 100);
			statusList = twitter.getUserTimeline(twitterUserId, paging).stream()
					.filter(status -> DateUtils.isSameDay(status.getCreatedAt(), new Date()))
					.collect(Collectors.toList());
		} catch (TwitterException e) {
			logger.warn("there is an issue in retreving twitter results:"+e.getMessage());
		}
		return statusList;
	}

}