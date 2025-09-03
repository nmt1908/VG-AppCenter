package com.ctrl.vgappcenter.core;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import com.ctrl.vgappcenter.access.MyAccService;

public class MyAccBridge {
    public static void goHomeThen(Context ctx, Runnable r) {
        if (!isEnabled(ctx)) {
            // mở settings để bật Accessibility cho App C
            Intent s = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            s.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(s);
            return;
        }
        MyAccService.goHomeThen(r);
    }

    private static boolean isEnabled(Context ctx) {
        try {
            String enabled = Settings.Secure.getString(
                    ctx.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            return enabled != null && enabled.contains(ctx.getPackageName() + "/.access.MyAccService");
        } catch (Exception e) { return false; }
    }
}
