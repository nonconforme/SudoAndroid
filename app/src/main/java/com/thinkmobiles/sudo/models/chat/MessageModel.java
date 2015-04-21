package com.thinkmobiles.sudo.models.chat;

/**
 * Created by omar on 21.04.15.
 */
public class MessageModel {
    private String messageText;
    private Long timeStamp;
    private boolean trueIfMessageWasRecieved;

    public boolean isTrueIfMessageWasRecieved() {
        return trueIfMessageWasRecieved;
    }

    public void setTrueIfMessageWasRecieved(boolean trueIfMessageWasRecieved) {
        this.trueIfMessageWasRecieved = trueIfMessageWasRecieved;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }


    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
