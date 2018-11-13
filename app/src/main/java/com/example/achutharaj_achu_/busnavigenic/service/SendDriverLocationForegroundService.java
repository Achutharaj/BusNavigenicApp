package com.example.achutharaj_achu_.busnavigenic.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.achutharaj_achu_.busnavigenic.utility.NotificationUtils;

public class SendDriverLocationForegroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public SendDriverLocationForegroundService(){}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void showNotification() {
    }
}
