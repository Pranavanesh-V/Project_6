package com.example.project_6;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Create and display the notification
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "channel_id")
                        .setSmallIcon(R.drawable.baseline_close_24)
                        .setContentTitle("Scheduled Notification")
                        .setContentText("This is a scheduled notification")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
    }
}

