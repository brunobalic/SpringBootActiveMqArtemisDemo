package com.brunobalic.activemqdemo;

import org.apache.activemq.artemis.api.core.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsSender.class);
    public static final String QUEUE_NAME = "queue1";

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        LOGGER.info("++++ Before sending to JMS.");
        jmsTemplate.convertAndSend(QUEUE_NAME, message);
    }

    public void sendMessage(String message, Instant deliverAt) {
        LOGGER.info("++++ Before sending: {}", message);
        jmsTemplate.convertAndSend(QUEUE_NAME, message, msg -> {
            msg.setLongProperty(Message.HDR_SCHEDULED_DELIVERY_TIME.toString(), deliverAt.toEpochMilli());
            return msg;
        });
    }

}
