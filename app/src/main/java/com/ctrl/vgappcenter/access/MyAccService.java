package com.ctrl.vgappcenter.access;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;

public class MyAccService extends AccessibilityService {
    static MyAccService sInstance;

    @Override public void onServiceConnected() { sInstance = this; }
    @Override public void onDestroy() { if (sInstance==this) sInstance = null; }
    @Override public void onAccessibilityEvent(AccessibilityEvent e) {}
    @Override public void onInterrupt() {}

    public static void goHomeThen(Runnable r) {
        if (sInstance == null) return;
        sInstance.performGlobalAction(GLOBAL_ACTION_HOME);
        new Handler(Looper.getMainLooper()).postDelayed(r, 500);
    }
}
