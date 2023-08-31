package com.example.project_6;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationForegroundService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start the service as a foreground service
        createNotificationChannel();
        Notification notification = createNotification();
        startForeground(1, notification);
        scheduleNotification(); // Schedule notifications here
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        // Create a notification channel for Android 8.0 and above
        // ...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification() {
        // Create and return a notification
        // ...
        createNotificationChannel();

        // Create a PendingIntent for the notification's action (optional)
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);

        // Build the notification using NotificationCompat.Builder
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "channel_id") // Specify the channel ID
                        .setSmallIcon(R.drawable.baseline_close_24) // Icon displayed in the notification
                        .setContentTitle("Scheduled Notification") // Title of the notification
                        .setContentText("This is a scheduled notification") // Content of the notification
                        .setContentIntent(pendingIntent) // Set the PendingIntent for the notification
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Priority of the notification
                        .setAutoCancel(true); // Auto-cancel the notification when clicked

        // Return the constructed notification
        return builder.build();
    }

    private void scheduleNotification() {
        // Schedule notifications using AlarmManager
        // ...
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8); // Set the hour of the notification
        calendar.set(Calendar.MINUTE, 0);     // Set the minute of the notification

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}

