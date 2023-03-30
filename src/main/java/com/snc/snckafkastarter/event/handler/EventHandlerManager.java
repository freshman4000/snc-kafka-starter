package com.snc.snckafkastarter.event.handler;

import com.snc.snckafkastarter.models.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.function.Consumer;

@AllArgsConstructor
@NoArgsConstructor
public class EventHandlerManager {
    private Map<String, Consumer<KafkaMessage>> eventHandlers;

    public Consumer<KafkaMessage> getEventHandler(String eventName) {
        return eventHandlers.get(eventName);
    }

    void addHandler(String eventName, Consumer<KafkaMessage> eventHandler) {
        eventHandlers.put(eventName, eventHandler);
    }

    Map<String, Consumer<KafkaMessage>> getEventHandlers() {
        return eventHandlers;
    }
}
