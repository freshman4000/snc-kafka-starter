package com.snc.snckafkastarter.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snc.snckafkastarter.JsonConverter;
import com.snc.snckafkastarter.models.Error;
import com.snc.snckafkastarter.models.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.snc.snckafkastarter.models.Headers.*;
import static com.snc.snckafkastarter.models.Headers.Values.SUCCESS;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageCreator {

    @Value("${spring.application.name}")
    private String applicationName;

    public KafkaMessage getSuccessMessage(Object body, Map<String, String> headers) {
        headers.put(STATUS, SUCCESS);
        headers.put(TIMESTAMP, ZonedDateTime.now(Clock.systemUTC()).format( DateTimeFormatter.ISO_ZONED_DATE_TIME));
        headers.put(APPLICATION, applicationName);

        return createMessage(body, headers);
    }

    public KafkaMessage getErrorMessage(Throwable throwable, Map<String, String> headers) {
        com.snc.snckafkastarter.models.Error error = new Error();
        error.setLocation(applicationName);
        error.setMsg(throwable.getMessage());
        error.setType(throwable.getClass().getSimpleName());

        headers.put(STATUS, throwable.getMessage());
        headers.put(APPLICATION, applicationName);
        headers.put(TIMESTAMP, ZonedDateTime.now(Clock.systemUTC()).format( DateTimeFormatter.ISO_ZONED_DATE_TIME));

        return createMessage(error, headers);
    }

    public KafkaMessage getMessage(Object body, Map<String, String> headers) {
        headers.put(TIMESTAMP, ZonedDateTime.now(Clock.systemUTC()).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        headers.put(APPLICATION, applicationName);

        return createMessage(body, headers);
    }

    private KafkaMessage createMessage(Object body, Map<String, String> headers) {
        KafkaMessage kafkaMessage = new KafkaMessage();
        try {
            if (body != null) {
                kafkaMessage.setMessageBody(JsonConverter.jsonFrom(body));
            }
        } catch (JsonProcessingException e) {
            kafkaMessage.setMessageBody("ERROR PARSE JSON");
        }
        kafkaMessage.setHeaders(headers);
        return kafkaMessage;
    }
}
