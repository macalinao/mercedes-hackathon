package pw.ian.hackwiththebest;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by ian on 6/13/15.
 */
public class AirTouchEmulator {
    public static final int CENTER_X = 1920 / 2;
    public static final int CENTER_Y = 1080 / 2;

    public static void sendCommand(String cmd, Object... args) {
        try {
            Unirest.post("http://localhost:21000/runjs").body(String.format(cmd, args)).asJson();
        } catch (UnirestException ex) {
        }
    }

    public static void tap(int x, int y) {
        sendCommand("tap(%d, %d, 100)", x, y);
    }

    public static void zoomInCentered(int magnitude, int duration) {
        int ax0 = CENTER_X - 50;
        int ay0 = CENTER_Y - 50;
        int ax1 = ax0 - magnitude;
        int ay1 = ay0 - magnitude;

        int bx0 = CENTER_X + 50;
        int by0 = CENTER_Y + 50;
        int bx1 = ax0 + magnitude;
        int by1 = ay0 + magnitude;

        sendCommand("pinch(%d, %d, %d, %d, %d, %d, %d, %d, %d, %d)",
                ax0, ay0, ax1, ay1, bx0, by0, bx1, by1, duration / 25, duration);
    }

    public static void zoomOutCentered(int magnitude, int duration) {
        int ax0 = CENTER_X - 50;
        int ay0 = CENTER_Y - 50;
        int ax1 = ax0 - magnitude;
        int ay1 = ay0 - magnitude;

        int bx0 = CENTER_X + 50;
        int by0 = CENTER_Y + 50;
        int bx1 = ax0 + magnitude;
        int by1 = ay0 + magnitude;

        sendCommand("pinch(%d, %d, %d, %d, %d, %d, %d, %d, %d, %d)",
                ax1, ay1, ax0, ay0, bx1, by1, bx0, by0, duration / 25, duration);
    }
}
