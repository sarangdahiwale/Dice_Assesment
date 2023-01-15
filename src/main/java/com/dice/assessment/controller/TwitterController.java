package com.dice.assessment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@RestController
public class TwitterController {

	Twitter twitter;
	@GetMapping("/tweets/user/")
	public ResponseEntity<?> getTweets(@RequestHeader("consumerkey") final String CONSUMER_KEY,
			@RequestHeader("consumersecret") final String CONSUMER_SECRET, @RequestParam final String query, @RequestParam final int page)
			throws Exception {
//        String CONSUMER_KEY ="OEDAH6flOK1a7aTvteKl1WVny";
//        String CONSUMER_SECRET="klrh3h8E5L9H6E2quh2V4J6agZdoa9HlCdHPY6jQDARKDrwzns";
		twitter=configure(CONSUMER_KEY, CONSUMER_SECRET);
		ResponseList<User> users = twitter.searchUsers(query, page);
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/tweets/{userId}")
	public ResponseEntity<?> getTweets(@RequestHeader("consumerkey") final String CONSUMER_KEY,
			@RequestHeader("consumersecret") final String CONSUMER_SECRET,@PathVariable final Long userId) throws TwitterException
	{
		twitter=configure(CONSUMER_KEY, CONSUMER_SECRET);
		ResponseList<Status> userTimeline = twitter.getUserTimeline(userId);
		return ResponseEntity.ok(userTimeline);
	}
	
	
	public Twitter configure(String CONSUMER_KEY,String CONSUMER_SECRET)
	{
		String ACCESS_TOKEN = "1614293189879566336-O8B4g9lsK7FxlRcuh44h6W7322Y5eA";
		String ACESS_TOKEN_SECRET = "odyWaJIlF93uYC34CtavjriuJLVcusJBrNwXYsecIYsDy";
		Twitter twitter = null;
		try {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(CONSUMER_KEY);
			builder.setOAuthConsumerSecret(CONSUMER_SECRET);
			builder.setOAuthAccessToken(ACCESS_TOKEN);
			builder.setOAuthAccessTokenSecret(ACESS_TOKEN_SECRET);

			Configuration configuration = builder.build();
			TwitterFactory factory = new TwitterFactory(configuration);
			twitter = factory.getInstance();
		} catch (Exception e) {
			// TODO: handle exception
			ResponseEntity.badRequest().body("Invalid Key or Secret");
		}
		return twitter;
	}
}
