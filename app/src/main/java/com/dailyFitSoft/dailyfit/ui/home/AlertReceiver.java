package com.dailyFitSoft.dailyfit.ui.home;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.dailyFitSoft.dailyfit.R;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        UpdateNotification(context);
    }

    private void UpdateNotification(Context context){
        Notification notification = new Notification.Builder(context.getApplicationContext(),"default")
                .setContentTitle("Przypomnienie")
                .setContentText("Masz zaplanowany trening!")
                .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
                .setAutoCancel(true)
                .build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(manager != null)
            manager.notify(123, notification);
    }
}
