package com.event_driven_ms.runner;

import twitter4j.TwitterException;

public interface StreamRunner {
    void start() throws TwitterException;
}
