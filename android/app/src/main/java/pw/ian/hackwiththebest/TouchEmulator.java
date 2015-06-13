package pw.ian.hackwiththebest;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by ian on 6/13/15.
 */
public class TouchEmulator {
    public static void sendTap(int x, int y) {
        sendCommand("tap " + x + " " + y);
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
            os.writeBytes("/system/bin/input " + cmd + "\n");
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
