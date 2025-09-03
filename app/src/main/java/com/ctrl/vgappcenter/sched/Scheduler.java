// com/ctrl/vgappcenter/sched/Scheduler.java
package com.ctrl.vgappcenter.sched;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Scheduler {
    public static void setExact(Context ctx, int reqCode, long at, String target) {
        Intent i = new Intent(ctx, SwitchReceiver.class).putExtra("TARGET", target);
        PendingIntent pi = PendingIntent.getBroadcast(ctx, reqCode, i,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= 23) am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, at, pi);
        else am.setExact(AlarmManager.RTC_WAKEUP, at, pi);
    }
    public static void cancel(Context ctx, int reqCode) {
        Intent i = new Intent(ctx, SwitchReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(ctx, reqCode, i,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        ((AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE)).cancel(pi);
    }
}
