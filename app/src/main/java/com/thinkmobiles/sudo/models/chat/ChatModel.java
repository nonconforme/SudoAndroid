package com.thinkmobiles.sudo.models.chat;

import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 21.04.15.
 */
public class ChatModel implements Serializable {
    private List<MessageModel> listMessages;


    public void addMessage(MessageModel message) {
        if (listMessages == null)
            listMessages = new ArrayList<>();
        listMessages.add(message);
    }

    public long getLastMessageTimestamp() {

        if (listMessages == null || listMessages.size() == 0)
            return 0;

        return listMessages.get(listMessages.size() - 1).getTimeStamp();

    }





    public List<MessageModel> getListMessages() {
        return listMessages;
    }

    public void setListMessages(List<MessageModel> listMessages) {
        this.listMessages = listMessages;
    }

}
