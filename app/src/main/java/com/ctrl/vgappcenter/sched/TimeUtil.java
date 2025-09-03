// com/ctrl/vgappcenter/sched/TimeUtil.java
package com.ctrl.vgappcenter.sched;

import java.util.Calendar;

public class TimeUtil {
    public static long nextMillis(String hhmm) {
        String[] p = hhmm.split(":"); int h=Integer.parseInt(p[0]), m=Integer.parseInt(p[1]);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND,0); c.set(Calendar.MILLISECOND,0);
        c.set(Calendar.HOUR_OF_DAY,h); c.set(Calendar.MINUTE,m);
        if (c.getTimeInMillis() <= System.currentTimeMillis()) c.add(Calendar.DATE,1);
        return c.getTimeInMillis();
    }
    public static long plusDays(long ms, int days) { return ms + days*24L*60*60*1000; }
}
