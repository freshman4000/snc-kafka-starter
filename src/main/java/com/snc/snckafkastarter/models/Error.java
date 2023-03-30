package com.snc.snckafkastarter.models;

public class Error {
    private String location;
    private String msg;
    private String type;

    public Error() {
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Error{");
        sb.append("location='").append(this.location).append('\'');
        sb.append(", msg='").append(this.msg).append('\'');
        sb.append(", type='").append(this.type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
