package com.snc.snckafkastarter.converters;

import com.snc.snckafkastarter.models.KafkaResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class KafkaResponseCreator {


    public ResponseEntity<KafkaResponseDto> createKafkaResponse(String traceId, String eventName) {
        KafkaResponseDto responseDto = new KafkaResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setTraceId(traceId);
        responseDto.setEventName(eventName);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public ResponseEntity<KafkaResponseDto> createErrorKafkaResponse(String message, String traceId) {
        KafkaResponseDto responseDto = new KafkaResponseDto();
        responseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDto.setMessage(message);
        responseDto.setTraceId(traceId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
