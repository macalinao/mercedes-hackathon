package com.benz.dashboard;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

public class BrightNess {

	public static BrightNess instance;
	public static Context ctx;

	public BrightNess() {
	}

	public static BrightNess getInstance(Context ctx) {
		BrightNess.ctx = ctx;
		if (instance == null)
			instance = new BrightNess();
		return instance;
	}

	public static final int BLACK_SCREEN = 0;
	public static final int DEFAULT_BRIGHTNESS = 128;
	public static final int MAX_BRIGHT = 255;

	public void setSystemBrightness(int value) {
		if (value < -1)
			value = DEFAULT_BRIGHTNESS;
		android.provider.Settings.System.putInt(ctx.getContentResolver(),
				android.provider.Settings.System.SCREEN_BRIGHTNESS, value);

	}

	public void changeAppBrightness(Context context, int brightness) {
		if (!(context instanceof Activity))
			return;
		Window window = ((Activity) context).getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		if (brightness == -1) {
			lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
		} else {
			lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
		}
		window.setAttributes(lp);
	}
	/*
	 * public int getScreenMode() { int screenMode = 0; try { screenMode =
	 * Settings.System.getInt(ctx.getContentResolver(),
	 * Settings.System.SCREEN_BRIGHTNESS_MODE); } catch (Exception
	 * localException) {
	 * 
	 * } return screenMode; }
	 * 
	 * public int getScreenBrightness() { int screenBrightness = 255; try {
	 * screenBrightness = Settings.System.getInt(ctx.getContentResolver(),
	 * Settings.System.SCREEN_BRIGHTNESS); } catch (Exception localException) {
	 * 
	 * } return screenBrightness; }
	 * 
	 * public void setScreenMode(int paramInt) { try {
	 * Settings.System.putInt(ctx.getContentResolver(),
	 * Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt); } catch (Exception
	 * localException) { localException.printStackTrace(); } }
	 * 
	 * public void saveScreenBrightness(int paramInt) { try {
	 * Settings.System.putInt(ctx.getContentResolver(),
	 * Settings.System.SCREEN_BRIGHTNESS, paramInt); } catch (Exception
	 * localException) { localException.printStackTrace(); } }
	 * 
	 * public void setScreenBrightness(int paramInt) { Window localWindow =
	 * ctx.getWindow(); WindowManager.LayoutParams localLayoutParams =
	 * localWindow .getAttributes(); float f = paramInt / 255.0F;
	 * localLayoutParams.screenBrightness = f;
	 * localWindow.setAttributes(localLayoutParams); }
	 */

}
