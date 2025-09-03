// com/ctrl/vgappcenter/sched/BootReceiver.java
package com.ctrl.vgappcenter.sched;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context ctx, Intent it) {
        Rescheduler.applyAndScheduleAll(ctx);
    }
}
