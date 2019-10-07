package com.example.pcdashboard.Manager;

import android.app.Application;

import com.example.pcdashboard.Utility.Notifications;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Notifications.createChannel(this);
    }
}