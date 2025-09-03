// com/ctrl/vgappcenter/core/AppSwitcher.java
package com.ctrl.vgappcenter.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class AppSwitcher {
    public static final String PKG_A = "com.papagoinc.pakkagoatv3";
    public static final String ACT_A  = "com.papagoinc.pakkagoatv3.activity.SplashScreenActivity";
    public static final String PKG_B = "com.example.facedetector";
    public static final String ACT_B  = "com.example.facedetector.MainActivity";

    public static void startA(Context ctx) { start(ctx, PKG_A, ACT_A); }
    public static void startB(Context ctx) { start(ctx, PKG_B, ACT_B); }

    public static void toA(Activity act) { try { act.stopLockTask(); } catch (Exception ignored) {} startA(act); }
    public static void toB(Activity act) { try { act.stopLockTask(); } catch (Exception ignored) {} startB(act); }

    private static void start(Context ctx, String pkg, String act) {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setClassName(pkg, act);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }
}
