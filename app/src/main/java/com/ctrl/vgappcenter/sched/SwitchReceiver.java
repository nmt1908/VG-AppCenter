// com/ctrl/vgappcenter/sched/SwitchReceiver.java
package com.ctrl.vgappcenter.sched;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SwitchReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context ctx, Intent i) {
        String target = i.getStringExtra("TARGET"); // "A" | "B"
        Intent k = new Intent(ctx, com.ctrl.vgappcenter.SwitchActivity.class);
        k.putExtra("TARGET", target);
        k.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(k);

        // Đặt lại alarm cho NGÀY MAI cùng giờ của target này (xem bước 5)
        Rescheduler.rescheduleTargetTomorrow(ctx, target);
    }
}
