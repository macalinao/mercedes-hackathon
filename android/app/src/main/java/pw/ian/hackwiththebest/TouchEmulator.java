package pw.ian.hackwiththebest;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by ian on 6/13/15.
 *
 * -- notes --
 * Source of input
 * https://android.googlesource.com/platform/frameworks/base/+/HEAD/cmds/input/src/com/android/commands/input/Input.java
 */
public class TouchEmulator {
    /**
     * Sends a tap at the given location.
     *
     * @param x
     * @param y
     */
    public static void sendTap(int x, int y) {
        if (x < 0 || y < 0) return;
        sendCommand("tap " + x + " " + y);
    }

    public static void sendText(String text) {
        sendCommand("text \"" + text + "\"");
    }

    /**
     * Sends an input related command.
     *
     * @param cmd
     */
    private static void sendCommand(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("/system/bin/input " + cmd + "\nj");
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            process.waitFor();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
