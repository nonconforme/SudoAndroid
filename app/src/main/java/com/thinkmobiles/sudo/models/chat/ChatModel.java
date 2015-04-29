package com.thinkmobiles.sudo.models.chat;

import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 21.04.15.
 */
public class ChatModel implements Serializable {
    private List<MessageModel> listMessages;

    private UserModel sender, receiver;
    private NumberModel senderNumber;
    private NumberModel receiverNumber;

    public ChatModel(UserModel sender, UserModel receiver, NumberModel senderNumber, NumberModel receiverNumber) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
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


    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }

    public UserModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserModel receiver) {
        this.receiver = receiver;
    }


    public void addMessage(MessageModel message) {
        if (listMessages == null) listMessages = new ArrayList<>();
        listMessages.add(message);
    }

    public long getLastMessageTimestamp() {

        if (listMessages == null || listMessages.size() == 0) return 0;

        return listMessages.get(listMessages.size() - 1).getTimeStamp();

    }


    public List<MessageModel> getListMessages() {
        return listMessages;
    }

    public void setListMessages(List<MessageModel> listMessages) {
        this.listMessages = listMessages;
    }

}
