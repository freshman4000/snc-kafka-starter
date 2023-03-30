package com.snc.snckafkastarter.event.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class HandlerConfiguration {

    @Bean
    EventHandlerManager eventHandlerManager() {
        return new EventHandlerManager(new HashMap<>());
    }
}
