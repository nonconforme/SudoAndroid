package com.thinkmobiles.sudo.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MainService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public MainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }


}
