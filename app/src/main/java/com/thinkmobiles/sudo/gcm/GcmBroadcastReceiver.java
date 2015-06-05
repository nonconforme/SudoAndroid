package com.thinkmobiles.sudo.gcm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());

        Log.d("Gcm  ", "Received");
        startWakefulService(context, (intent.setComponent(comp)));
    }
}