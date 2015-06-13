package pw.ian.hackwiththebest;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by ian on 6/13/15.
 */
public class TouchEmulator {
    private void sendCommand(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("/system/bin/input " + cmd);
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
