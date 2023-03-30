package com.snc.snckafkastarter.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "spring.kafka.producer")
@Data
public class KafkaProducerProperties {

    private List<String> bootstrapServers;
}