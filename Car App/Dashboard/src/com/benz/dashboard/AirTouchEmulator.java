package com.benz.dashboard;


/**
 * Created by ian on 6/13/15.
 */
public class AirTouchEmulator {
	public static final int CENTER_X = 1920 / 2;
	public static final int CENTER_Y = 1080 / 2;

	public static void sendCommand(String cmd, Object... args) {

		// String command =
		// "/data/local/tmp/air-native -i /dev/input/event2 -runjs='"
		// + String.format(cmd, args) + "'";
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
				ax1, ay1, bx0, by0, bx1, by1, duration / 25, duration);
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

		sendCommand("pinch(%d, %d, %d, %d, %d, %d, %d, %d, %d, %d)", ax1, ay1,
				ax0, ay0, bx1, by1, bx0, by0, duration / 25, duration);
	}

}
