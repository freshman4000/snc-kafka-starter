package com.snc.snckafkastarter.models;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseDto {
    protected static final DateTimeFormatter formatter;
    protected int status;
    protected String path;
    protected String timestamp;
    protected String message;
    protected String traceId;

    public ResponseDto() {
        this.timestamp = ZonedDateTime.now(Clock.systemUTC()).format(formatter);
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    static {
        formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
    }
}
