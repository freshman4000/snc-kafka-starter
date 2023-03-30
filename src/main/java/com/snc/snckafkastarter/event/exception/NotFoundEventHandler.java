package com.snc.snckafkastarter.event.exception;

public class NotFoundEventHandler extends RuntimeException {
    private static final String MESSAGE = "Not found handler for event %s.";
    public NotFoundEventHandler(String eventName) {
        super(String.format(MESSAGE, eventName));
    }
}
