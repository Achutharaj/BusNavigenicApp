package com.example.achutharaj_achu_.busnavigenic.utility;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;


import com.example.achutharaj_achu_.busnavigenic.R;

import java.util.Map;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "com.trivyol.trivyol_android_app.utility";
    public static final String ANDROID_CHANNEL_NAME = "Show";
    public static final String COMPLETED_MISSION_ID = "com.trivyol.trivyol_android_app.utility1";
    public static final String COMPLETED_MISSION_CHANNEL = "Completed";

    public static final String ACCEPTED_MISSION_ID = "com.trivyol.trivyol_android_app.utility2";
    public static final String ACCEPTED_MISSION_CHANNEL = "Completed";

    public NotificationUtils(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {

        // create android channel
        NotificationChannel androidChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT); // Sets whether notifications posted to this channel should display notification lights
            androidChannel.enableLights(true);
            androidChannel.enableVibration(true);
            androidChannel.setLightColor(Color.GREEN);
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(androidChannel);

        }
        NotificationChannel completedMissionChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            completedMissionChannel = new NotificationChannel(COMPLETED_MISSION_ID,
                    COMPLETED_MISSION_CHANNEL, NotificationManager.IMPORTANCE_HIGH); // Sets whether notifications posted to this channel should display notification lights
            completedMissionChannel.enableLights(true);
            completedMissionChannel.enableVibration(true);
            completedMissionChannel.setLightColor(Color.GREEN);
            completedMissionChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(completedMissionChannel);

        }

        NotificationChannel acceptedMissionChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            acceptedMissionChannel = new NotificationChannel(COMPLETED_MISSION_ID,
                    COMPLETED_MISSION_CHANNEL, NotificationManager.IMPORTANCE_HIGH); // Sets whether notifications posted to this channel should display notification lights
//            acceptedMissionChannel.enableLights(true);
//            acceptedMissionChannel.enableVibration(true);

            acceptedMissionChannel.setLightColor(Color.GREEN);
            acceptedMissionChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(acceptedMissionChannel);

        }


    }




    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }


    private PendingIntent createPendingIntent(Intent intent) {
        return PendingIntent
                .getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
    }

}