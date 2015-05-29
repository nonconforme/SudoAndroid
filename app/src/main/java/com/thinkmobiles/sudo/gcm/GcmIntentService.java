package com.thinkmobiles.sudo.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.thinkmobiles.sudo.Main_Activity;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.LoginActivity;
import com.thinkmobiles.sudo.global.App;


public class GcmIntentService extends IntentService {
    final int NOTIFICATION_ID = 1;
    static final String TAG = "GcmIntentService";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                sendNotification("Received: " + extras.toString());
                Log.i(TAG, "Received: " + extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void sendNotification(final String msg) {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent;
        if (App.getCurrentUser() == null || App.getCurrentUser().getCompanion().isEmpty()) {
            contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, LoginActivity.class), 0);
        } else {
            contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, Main_Activity.class), 0);
        }


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_big_man).setContentTitle(getResources().getString(R.string.app_name)).setVibrate(new long[]{1000, 500, 500, 500, 1000}).setLights(Color.RED, 3000, 3000).setStyle(new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}