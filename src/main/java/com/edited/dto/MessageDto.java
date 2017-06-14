package com.edited.dto;

import java.time.LocalDateTime;


public class MessageDto {

    private LocalDateTime time;
    private String chatName;
    private String fromLoginName;
    private String fromDisplayName;
    private String dialogPartner;
    private String message;

    public MessageDto(LocalDateTime time, String chatName, String fromLoginName, String fromDisplayName, String dialogPartner, String message) {
        this.time = time;
        this.chatName = chatName;
        this.fromLoginName = fromLoginName;
        this.fromDisplayName = fromDisplayName;
        this.dialogPartner = dialogPartner;
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getChatName() {
        return chatName;
    }

    public String getFromLoginName() {
        return fromLoginName;
    }

    public String getFromDisplayName() {
        return fromDisplayName;
    }

    public String getDialogPartner() {
        return dialogPartner;
    }

    public String getMessage() {
        return message;
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
