package com.brunobalic.activemqdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsSender.class);

    @JmsListener(destination = JmsSender.QUEUE_NAME)
    public void onMessage(String message) {
        LOGGER.info("++++ Received message from JMS, message: {}", message);
    }

}
