// com/ctrl/vgappcenter/sched/Rescheduler.java
package com.ctrl.vgappcenter.sched;

import android.content.Context;
import java.util.*;

public class Rescheduler {
    // lưu giờ dạng "HH:mm" cho A/B (có thể cho phép nhiều mốc mỗi ngày)
    public static List<String> timesToA = new ArrayList<>();
    public static List<String> timesToB = new ArrayList<>();

    public static void applyAndScheduleAll(Context ctx) {
        // huỷ cũ
        for (int i=0;i<64;i++){ Scheduler.cancel(ctx, 2000+i); Scheduler.cancel(ctx, 3000+i); }
        // đặt lại
        for (int i=0;i<timesToA.size();i++) Scheduler.setExact(ctx, 2000+i, TimeUtil.nextMillis(timesToA.get(i)),"A");
        for (int i=0;i<timesToB.size();i++) Scheduler.setExact(ctx, 3000+i, TimeUtil.nextMillis(timesToB.get(i)),"B");
    }

    public static void rescheduleTargetTomorrow(Context ctx, String target) {
        List<String> list = "A".equals(target) ? timesToA : timesToB;
        int base = "A".equals(target) ? 2000 : 3000;
        for (int i=0;i<list.size();i++) {
            long next = TimeUtil.plusDays(TimeUtil.nextMillis(list.get(i)), 1);
            Scheduler.setExact(ctx, base+i, next, target);
        }
    }
}
