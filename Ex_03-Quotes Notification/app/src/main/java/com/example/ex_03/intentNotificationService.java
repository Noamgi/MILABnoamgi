package com.example.ex_03;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;

public class intentNotificationService extends IntentService {

    private static final String ACTION_NOTIFICATION = "com.example.ex_03.action.NOTIFICATION";

    public intentNotificationService(){
        super("intentNotificationService");
    }

    public static void makeAction(Context context) {
        Intent intent = new Intent(context, intentNotificationService.class);
        intent.setAction(ACTION_NOTIFICATION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NOTIFICATION.equals(action)) {
                handleNotificationAction();
            } else
                {
                throw new RuntimeException("An unknown action");
                }
        }
    }


private void handleNotificationAction(){

    Intent intent = new Intent(getApplicationContext(), broadcastRecevier.class);
    AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES / 3, pendingIntent);
}
}
