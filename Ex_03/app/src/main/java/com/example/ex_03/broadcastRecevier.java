package com.example.ex_03;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.os.Build;
import static android.content.Context.NOTIFICATION_SERVICE;


public class broadcastRecevier extends BroadcastReceiver {
    protected int NotificationId = 1;


    @Override
        public void onReceive(Context context, Intent intent) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                NotificationManager notificationManager = (NotificationManager)context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(new NotificationChannel("NotifyChannel","NotifyChannel",NotificationManager.IMPORTANCE_DEFAULT));
            }

            String quote =  quotesNotification.getQuote();

            Intent theIntent = new Intent(context, MainActivity.class);
            theIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), theIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotifyChannel")
                    .setSmallIcon(R.drawable.icon)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("Love Quote")
                    .setContentText(quote)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(quote))
                    .setAutoCancel(true);


            NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NotificationId++, builder.build());
        }
    }

