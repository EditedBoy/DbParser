package com.edited.dto;

import java.time.LocalDateTime;


public class MessageDto {

    private LocalDateTime time;
    private String chatName;
    private String fromLoginName;
    private String fromDisplayName;
    private String message;

    public MessageDto(LocalDateTime time, String chatName, String fromLoginName, String fromDisplayName, String message) {
        this.time = time;
        this.chatName = chatName;
        this.fromLoginName = fromLoginName;
        this.fromDisplayName = fromDisplayName;
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getFromLoginName() {
        return fromLoginName;
    }

    public void setFromLoginName(String fromLoginName) {
        this.fromLoginName = fromLoginName;
    }

    public String getFromDisplayName() {
        return fromDisplayName;
    }

    public void setFromDisplayName(String fromDisplayName) {
        this.fromDisplayName = fromDisplayName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullMessage() {
        return String.format("[%s] %s (%s): %s", time.toLocalDate() + " " + time.toLocalTime(), fromDisplayName, fromLoginName, message);
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "time=" + time +
                ", chatName='" + chatName + '\'' +
                ", fromLoginName='" + fromLoginName + '\'' +
                ", fromDisplayName='" + fromDisplayName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
