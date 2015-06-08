package com.thinkmobiles.sudo.models.chat;

/**
 * Created by njakawaii on 07.05.2015.
 */
public class LastChatsModel {
    private int unread;
    private MessageModel lastmessage;

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public MessageModel getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(MessageModel lastmessage) {
        this.lastmessage = lastmessage;
    }


}
