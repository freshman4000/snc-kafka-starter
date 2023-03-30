package com.snc.snckafkastarter.models;

import java.time.Clock;
import java.time.ZonedDateTime;

public class KafkaResponseDto extends ResponseDto{
    private String eventName;
    public KafkaResponseDto() {
        this.timestamp = ZonedDateTime.now(Clock.systemUTC()).format(formatter);
    }

    public String getEventName() {
        return eventName;
    }

    public KafkaResponseDto setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }
}
