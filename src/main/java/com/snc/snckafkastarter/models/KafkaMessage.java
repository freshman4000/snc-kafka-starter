package com.snc.snckafkastarter.models;

import java.util.Map;

public class KafkaMessage {
    private String messageBody;
    private Map<String, String> headers = Map.of();

    public KafkaMessage() {
    }

    public String getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{\n");
        sb.append(",\n\"messageBody\":\"").append(this.messageBody).append('"');
        sb.append(",\n\"headers\":").append(this.headers);
        sb.append("\n}");
        return sb.toString();
    }
}
