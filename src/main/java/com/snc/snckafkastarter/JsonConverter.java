package com.snc.snckafkastarter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class JsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static <T> T objectFromJson(String json, Class<T> tClass) throws JsonProcessingException {
        objectMapper.registerModule(new JSR310Module());
        objectMapper.setDateFormat(new SimpleDateFormat(FORMAT));
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper.readValue(json, tClass);
    }

    public static <T> T objectFromJson(String json, TypeReference<T> valueTypeRef) throws JsonProcessingException {
        objectMapper.registerModule(new JSR310Module());
        objectMapper.setDateFormat(new SimpleDateFormat(FORMAT));
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper.readValue(json, valueTypeRef);
    }

    public static String jsonFrom(Object object) throws JsonProcessingException {
        objectMapper.registerModule(new JSR310Module());
        objectMapper.setDateFormat(new SimpleDateFormat(FORMAT));
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper.writeValueAsString(object);
    }
}
