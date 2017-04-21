package com.TT.kitcoop.ttuburgi.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

public class MyService extends Service {
    AlarmManager alarmManager;
    PendingIntent broadcast;

    String alarmIntent;

    int hour;
    int minute;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if(intent != null) {
            alarmIntent = intent.getStringExtra(("alarm"));
            System.out.println(alarmIntent + "없어");
        } else {
            System.out.println(alarmIntent + "있어");
        }

        String alarm[] = alarmIntent.split(":");
        System.out.println(alarm[0] + alarm[1]);

        hour = Integer.parseInt(alarm[0]);
        minute = Integer.parseInt(alarm[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 10);

        System.out.println(hour + "" + minute);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        broadcast = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(alarmManager != null) {
            alarmManager.cancel(broadcast);
        }
    }
}