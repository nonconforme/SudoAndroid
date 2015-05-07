package com.thinkmobiles.sudo.models.chat;

import java.util.List;

/**
 * Created by njakawaii on 07.05.2015.
 */
public class LastChatsModel {
    public MessageModel getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(MessageModel lastmessage) {
        this.lastmessage = lastmessage;
    }

    private MessageModel lastmessage;
}
