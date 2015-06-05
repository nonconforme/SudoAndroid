package com.thinkmobiles.sudo.models;

/**
 * Created by omar on 05.06.15.
 */
public class DeviceID {
    String channelId;
    String provider;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
