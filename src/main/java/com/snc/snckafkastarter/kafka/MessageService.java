package com.snc.snckafkastarter.kafka;

import com.snc.snckafkastarter.models.Headers;
import com.snc.snckafkastarter.models.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
    @Value("${spring.kafka.topics.snc-producer-topic}")
    private String topic;
    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void sendMessage(KafkaMessage message) {
        LOGGER.info("[traceId = {}]Message sent to topic='{}': {}",
                message.getHeaders().get(Headers.X_TRACE_ID),
                topic,
                message);
        kafkaTemplate.send(topic, message);
    }

    public void sendMessage(KafkaMessage message, String topicName) {
        LOGGER.info("[traceId = {}]Message sent to topic='{}': {}",
                message.getHeaders().get(Headers.X_TRACE_ID),
                topicName,
                message);
        kafkaTemplate.send(topicName, message);
    }
}
