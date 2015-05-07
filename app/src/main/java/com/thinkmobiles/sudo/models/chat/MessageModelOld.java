package com.thinkmobiles.sudo.models.chat;

import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.io.Serializable;

/**
 * Created by omar on 21.04.15.
 */
public class MessageModelOld implements Serializable {
    private String messageText;
    private Long timeStamp;
    private UserModel sender;


    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
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
