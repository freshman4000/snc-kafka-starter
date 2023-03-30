package com.snc.snckafkastarter.kafka;

import com.snc.snckafkastarter.event.exception.NotFoundEventHandler;
import com.snc.snckafkastarter.event.handler.EventHandlerManager;
import com.snc.snckafkastarter.models.Headers;
import com.snc.snckafkastarter.models.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private KafkaMessage message = null;

    @Autowired
    private EventHandlerManager eventHandlerMapping;

    @KafkaListener(topics = "${spring.kafka.topics.snc-service-topic}", containerFactory = "eventKafkaListenerContainerFactory")
    public void receive(KafkaMessage message) {
        LOGGER.info("[traceId = {}]Received message ='{}'", message.toString(), message.getHeaders().get(Headers.X_TRACE_ID));
        setMessage(message);
        latch.countDown();
        handleMessage(message);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public KafkaMessage getMessage() {
        return message;
    }

    private void setMessage(KafkaMessage message) {
        this.message = message;
    }


    private void handleMessage(KafkaMessage message) {
        String eventName = message.getHeaders().get(Headers.EVENT_NAME);
        Consumer<KafkaMessage> eventHandler = eventHandlerMapping.getEventHandler(eventName);
        if (eventHandler == null) {
            throw new NotFoundEventHandler(eventName);
        }
        eventHandler.accept(message);
    }
}
