package com.ctrl.vgappcenter.core;

public class RootShell {
    public static boolean isRootAvailable() {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"su","-c","id"});
            int rc = p.waitFor();
            return rc == 0;
        } catch (Exception e) { return false; }
    }
    public static void exec(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"su","-c", cmd});
            p.waitFor();
        } catch (Exception ignored) {}
    }
}
