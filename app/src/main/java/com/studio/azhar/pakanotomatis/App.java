package com.studio.azhar.pakanotomatis;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


//ini berfungsi untuk membuat channel notifikasi, sehingga notif bisa muncul
public class App extends Application {
    public static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();//menjalankan fungsi membuat notifikasi
    }

    //membuat notifikasi agar bisa berjalan di android OS diatas OREO
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);//notif manager berfungsi untuk mengatur notifikasi di android

        }
    }
}
