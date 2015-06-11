package com.thinkmobiles.sudo.gcm;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.ChatActivity;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.utils.NotificationNameAndAvatarHelper;
import com.thinkmobiles.sudo.utils.StoreNotification;

import java.text.SimpleDateFormat;
import java.util.Date;


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
                /*sendNotification("Send error: " + extras.toString());*/
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                /*sendNotification("Deleted messages on server: " + extras.toString());*/
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

                Log.i(TAG, "Received: " + extras.getString(Constants.TEXT));
                Log.i(TAG, "Received: " + extras.getString(Constants.SENDER));
                Log.i(TAG, "Received: " + extras.getString(Constants.RECEIVER));
                if (!StoreNotification.loadNotificationStatus(this)) {
                    sendNotification(extras.getString(Constants.SENDER), extras.getString(Constants.RECEIVER), extras.getString(Constants.TEXT));
                }
                sendReloadChatsBroadcast();
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void sendNotification(String sender, String receiver, String text) {



        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_message);
        contentView.setImageViewResource(R.id.ivNotivIcon_NT, R.mipmap.ic_launcher);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(new Date());
        String[] senderDetailes = new String[]{"",""};
        if(App.getContactsList()!=null) {
             senderDetailes = NotificationNameAndAvatarHelper.discoverCompanionNameAndAvatar(sender);
        }
        String avatar = senderDetailes[1];
        contentView.setTextViewText(R.id.tvSenderNumber_NT, senderDetailes[0]);

        contentView.setTextViewText(R.id.tvTimedate_NT, currentDateandTime);
        contentView.setTextViewText(R.id.tvMessagePreview_NT, text);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_big_man).setContentTitle(getResources().getString(R.string.app_name)).setVibrate(new long[]{1000, 500, 500, 500, 1000}).setLights(Color.RED, 3000, 3000).setContent(contentView).setAutoCancel(true);


        mBuilder.setContentIntent(setPendingIntent(sender, receiver, avatar));
        if (App.getCurrentUser() != null && !isCurrentChat(sender, receiver)) {
            App.getmNotificationManager().notify(NOTIFICATION_ID, mBuilder.build());
        }
    }
    private boolean isCurrentChat(String receiver, String sender){
        String [] thisChat = {receiver,sender};
        if(App.getCurrentChat() == null)
            return false;
        if(App.getCurrentChat()[0].equalsIgnoreCase(thisChat[0]) && App.getCurrentChat()[1].equalsIgnoreCase
                (thisChat[1]))
            return true;

        return false;
    }
    private void sendReloadChatsBroadcast() {
        Intent reloadChats = new Intent(Constants.UPDATE_CHAT_LIST);
        sendBroadcast(reloadChats);
    }

    private PendingIntent setPendingIntent(String sender, String receiver, String avatar) {
        PendingIntent contentIntent;
        Intent intent = new Intent(this, ChatActivity.class);

        Bundle b = new Bundle();
        b.putString(Constants.FROM_NUMBER, receiver);
        b.putString(Constants.TO_NUMBER, sender);
        b.putString(Constants.AVATAR,  avatar);


        intent.putExtra(Constants.BUNDLE, b);

        contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return contentIntent;

    }


}