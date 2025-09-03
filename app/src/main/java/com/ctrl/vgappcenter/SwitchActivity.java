// com/ctrl/vgappcenter/ui/SwitchActivity.java
package com.ctrl.vgappcenter;

import android.app.Activity;
import android.os.Bundle;
import com.ctrl.vgappcenter.core.AppSwitcher;

public class SwitchActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try { stopLockTask(); } catch (Exception ignored) {}
        String target = getIntent().getStringExtra("TARGET"); // "A" | "B"
        if ("A".equals(target)) AppSwitcher.startA(this);
        else                    AppSwitcher.startB(this);
        finish();
    }
}
