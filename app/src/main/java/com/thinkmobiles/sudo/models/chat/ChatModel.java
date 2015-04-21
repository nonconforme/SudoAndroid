package com.thinkmobiles.sudo.models.chat;

import com.thinkmobiles.sudo.models.addressbook.NumberModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 21.04.15.
 */
public class ChatModel {
    private List<MessageModel> listMessages;
    private String companion;
    private NumberModel senderNumber;
    private NumberModel receiverNumber;


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

    public NumberModel getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(NumberModel senderNumber) {
        this.senderNumber = senderNumber;
    }

    public NumberModel getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(NumberModel receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public List<MessageModel> getListMessages() {
        return listMessages;
    }

    public void setListMessages(List<MessageModel> listMessages) {
        this.listMessages = listMessages;
    }

    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }


}
