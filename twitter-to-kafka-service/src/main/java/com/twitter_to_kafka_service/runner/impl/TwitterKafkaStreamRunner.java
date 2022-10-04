package com.twitter_to_kafka_service.runner.impl;

import com.twitter_to_kafka_service.config.ConfigData;
import com.twitter_to_kafka_service.listener.TwitterKafkaStatusListener;
import com.twitter_to_kafka_service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-v2-tweets",havingValue = "true",matchIfMissing = true)
//@ConditionalOnExpression("${twitter-to-kafka-service.enable-mock-tweets} && not ${twitter-to-kafka-service.enable-v2-tweets}")
public class TwitterKafkaStreamRunner implements StreamRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);

    private final ConfigData twitterToKafkaConfigData;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private TwitterStream twitterStream;

    public TwitterKafkaStreamRunner(ConfigData twitterToKafkaConfigData,
                                    TwitterKafkaStatusListener twitterKafkaStatusListener) {
        this.twitterToKafkaConfigData = twitterToKafkaConfigData;
        this.twitterKafkaStatusListener = twitterKafkaStatusListener;
    }

    @Override
    public void start() throws TwitterException {
        twitterStream = TwitterStreamFactory.getSingleton();
        twitterStream.addListener(twitterKafkaStatusListener);
        addFilter();
    }

    /*
    * shutdown streamer when app goes to shutdown
    * @PreDestroy used for clean-up before application shutdown
    * @PreDestroy is not called when the bean scope is prototype
    * */
    @PreDestroy
    public void shutdown(){
        if (twitterStream!=null){
            LOG.info("closing twitter stream");
            twitterStream.shutdown();
        }
    }

    private void addFilter() {
        //read keywords from config file
        String[] keywords = twitterToKafkaConfigData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        LOG.info("Started filtering twitter stream for keywords {}", Arrays.toString(keywords));
    }
}
