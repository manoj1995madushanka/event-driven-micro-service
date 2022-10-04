package com.twitter_to_kafka_service.runner.impl.mock;

import com.twitter_to_kafka_service.config.ConfigData;
import com.twitter_to_kafka_service.exception.TwitterToKafkaServiceException;
import com.twitter_to_kafka_service.listener.TwitterKafkaStatusListener;
import com.twitter_to_kafka_service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;


/*
 * used to mock twitter stream data because developer account can not get
 * */
@Component
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "true")
public class MockKafkaStreamRunner implements StreamRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MockKafkaStreamRunner.class);
    private final ConfigData configData;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private static final Random RANDOM = new Random();

    private static final String[] WORDS = new String[]{
            "lorem",
            "ipsom",
            "test",
            "hjgsah",
            "gxdqh", "kdsxcbg", "erg", "erg", "erg", "reg", "erg", "rg",
            "argwe", "sgre", "aweg", "aerg", "ag3ew4t", "agr43g", "bereb"
    };

    private static final String tweetAsRawJson = "{" +
            "\"created_at\":\"{0}\"," +
            "\"id\":\"{1}\"," +
            "\"text\":\"{2}\"," +
            "\"user\":{\"id\":\"{3}\"}" +
            "}";

    private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    public MockKafkaStreamRunner(ConfigData configData, TwitterKafkaStatusListener twitterKafkaStatusListener) {
        this.configData = configData;
        this.twitterKafkaStatusListener = twitterKafkaStatusListener;
    }

    @Override
    public void start() throws TwitterException {
        String[] keywords = configData.getTwitterKeywords().toArray(new String[0]);
        int minTweetLength = configData.getMockMinTweetLength();
        int maxTweetLength = configData.getMockMaxTweetLength();
        long sleepTimeMs = configData.getMockSleepMs();
        LOG.info("Starting mock filtering twitter streams for keywords {}.", Arrays.toString(keywords));
        simulateTwitterStream(keywords, minTweetLength, maxTweetLength, sleepTimeMs);
    }

    /*
     * executing in a separate thread
     * */
    private void simulateTwitterStream(String[] keywords, int minTweetLength, int maxTweetLength, long sleepTimeMs) throws TwitterException {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                String formattedTweetAsRawJson = getFormattedTweet(keywords, minTweetLength, maxTweetLength);
                Status status = TwitterObjectFactory.createStatus(formattedTweetAsRawJson);
                twitterKafkaStatusListener.onStatus(status);
                try {
                    Thread.sleep(sleepTimeMs);
                } catch (InterruptedException e) {
                    throw new TwitterToKafkaServiceException("Error while sleeping for waiting new status to create!");
                }
            }
        });
    }

    private String getFormattedTweet(String[] keywords, int minTweetLength, int maxTweetLength) {
        String[] params = new String[]{
                ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH)),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
                getRandomTweetContent(keywords, minTweetLength, maxTweetLength),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE))
        };
        return formatTweetAsJsonWithParams(params);
    }

    private String formatTweetAsJsonWithParams(String[] params) {
        String tweet = tweetAsRawJson;
        for (int i = 0; i < params.length; i++) {
            tweet = tweet.replace("{" + i + "}", params[i]);
        }
        return tweet;
    }

    private String getRandomTweetContent(String[] keywords, int minTweetLength, int maxTweetLength) {
        StringBuilder tweet = new StringBuilder();
        int tweetLength = RANDOM.nextInt(maxTweetLength - minTweetLength + 1) + minTweetLength;
        return constructRandomTweet(keywords, tweet, tweetLength);
    }

    private String constructRandomTweet(String[] keywords, StringBuilder tweet, int tweetLength) {
        for (int i = 0; i < tweetLength; i++) {
            tweet.append(WORDS[RANDOM.nextInt(WORDS.length)]).append(" ");
            if (i == tweetLength / 2) {
                tweet.append(keywords[RANDOM.nextInt(keywords.length)]).append(" ");
            }
        }
        return tweet.toString().trim();
    }


}
