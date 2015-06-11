package com.thinkmobiles.sudo.models.chat;

/**
 * Created by omar on 11.06.15.
 */
public class VoiceResponceModel {

    private MessageModel message;
    private String success;

    public MessageModel getMessage() {
        return message;
    }

    public void setMessage(MessageModel message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
