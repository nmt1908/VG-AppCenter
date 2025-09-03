package com.ctrl.vgappcenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ctrl.vgappcenter.core.AppSwitcher;
import com.ctrl.vgappcenter.sched.SwitchReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etTimesToB;
    private EditText etTimesToA;
    private SharedPreferences prefs;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("schedules", MODE_PRIVATE);

        etTimesToB = new EditText(this);
        etTimesToB.setHint("Times to switch to B (one per line, HH:mm)");
        etTimesToB.setText(prefs.getString("timesB", ""));

        etTimesToA = new EditText(this);
        etTimesToA.setHint("Times to switch to A (one per line, HH:mm)");
        etTimesToA.setText(prefs.getString("timesA", ""));

        Button btnApply = new Button(this);
        btnApply.setText("Apply & Schedule");
        btnApply.setOnClickListener(v -> {
            prefs.edit()
                    .putString("timesB", etTimesToB.getText().toString())
                    .putString("timesA", etTimesToA.getText().toString())
                    .apply();
            scheduleAll();
            toast("Đã lên lịch");
        });

        // Test bằng broadcast
        Button btnTestB = new Button(this);
        btnTestB.setText("Test now → B");
        btnTestB.setOnClickListener(v -> sendTarget("B"));

        Button btnTestA = new Button(this);
        btnTestA.setText("Test now → A");
        btnTestA.setOnClickListener(v -> sendTarget("A"));

        // Switch trực tiếp
        Button btnDirectB = new Button(this);
        btnDirectB.setText("Switch directly → B");
        btnDirectB.setOnClickListener(v -> AppSwitcher.toB(this));

        Button btnDirectA = new Button(this);
        btnDirectA.setText("Switch directly → A");
        btnDirectA.setOnClickListener(v -> AppSwitcher.toA(this));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 24, 24, 24);
        root.addView(etTimesToB);
        root.addView(etTimesToA);
        root.addView(btnApply);
        root.addView(btnTestA);
        root.addView(btnTestB);
        root.addView(btnDirectA);
        root.addView(btnDirectB);
        setContentView(root);
    }

    private void scheduleAll() {
        cancelAll();

        List<String> toB = parseLines(prefs.getString("timesB", ""));
        List<String> toA = parseLines(prefs.getString("timesA", ""));

        for (String hhmm : toB) scheduleOne(hhmm, "B");
        for (String hhmm : toA) scheduleOne(hhmm, "A");
    }

    private void cancelAll() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        for (int id = 1000; id < 1100; id++) {
            PendingIntent pi = PendingIntent.getBroadcast(
                    this, id, new Intent(this, SwitchReceiver.class),
                    PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
            );
            if (pi != null) am.cancel(pi);
        }
    }

    private void scheduleOne(String hhmm, String target) {
        try {
            String[] p = hhmm.split(":");
            int h = Integer.parseInt(p[0]);
            int m = Integer.parseInt(p[1]);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            c.set(Calendar.HOUR_OF_DAY, h);
            c.set(Calendar.MINUTE, m);
            if (c.getTimeInMillis() <= System.currentTimeMillis())
                c.add(Calendar.DATE, 1);

            Intent i = new Intent(this, SwitchReceiver.class);
            i.putExtra("TARGET", target);
            PendingIntent pi = PendingIntent.getBroadcast(
                    this, target.equals("A") ? (1000 + h*60+m) : (1050 + h*60+m),
                    i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
        } catch (Exception ignore) {}
    }

    private void sendTarget(String target) {
        Intent i = new Intent(this, SwitchReceiver.class);
        i.putExtra("TARGET", target);
        sendBroadcast(i);
    }

    private List<String> parseLines(String s) {
        List<String> out = new ArrayList<>();
        if (s == null) return out;
        for (String line : s.split("\\r?\\n")) {
            String t = line.trim();
            if (t.matches("\\d{2}:\\d{2}")) out.add(t);
        }
        return out;
    }

    private void toast(String m) { Toast.makeText(this, m, Toast.LENGTH_SHORT).show(); }
}
