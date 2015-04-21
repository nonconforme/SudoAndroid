package com.thinkmobiles.sudo.models.chat;

/**
 * Created by omar on 21.04.15.
 */
public class MessageModel {
    private String messageText;
    private Boolean recieved;
    private Long timeStamp;


    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Boolean getSender() {
        return recieved;
    }

    public void setSender(Boolean recieved) {
        this.recieved = recieved;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
