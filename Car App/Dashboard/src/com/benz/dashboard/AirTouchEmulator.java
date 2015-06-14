package com.benz.dashboard;

import android.util.Log;

/**
 * Created by ian on 6/13/15.
 */
public class AirTouchEmulator {
	public static final int CENTER_X = 1920 / 2 + 400;
	public static final int CENTER_Y = 1200 / 2;

	public static void sendCommand(String cmd, Object... args) {

		String command = "/data/local/tmp/air-native -i /dev/input/event2 -runjs='"
				+ String.format(cmd, args) + "'";
		Log.e("Command", command);

		//
		// try {
		// Process proc = Runtime.getRuntime().exec(command);
		// Log.e("Command", command);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		new GestureRequest().execute(String.format(cmd, args));
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

		sendCommand("pinch(%d, %d, %d, %d, %d, %d, %d, %d, %d, %d)", ax0, ay0,
				ax1, ay1, bx0, by0, bx1, by1, 1, duration);
	}

	public static void zoomOutCentered(int magnitude, int duration) {
		int ax0 = CENTER_X;
		int ay0 = CENTER_Y;
		int ax1 = CENTER_X - magnitude - 50;
		int ay1 = CENTER_Y - magnitude - 50;

		int bx0 = CENTER_X;
		int by0 = CENTER_Y;
		int bx1 = CENTER_X + magnitude + 50;
		int by1 = CENTER_Y + magnitude + 50;

		sendCommand("pinch(%d, %d, %d, %d, %d, %d, %d, %d, %d, %d)", ax1, ay1,
				bx1, by1, ax0, ay0, bx0, by0, 1, duration);
	}
}
