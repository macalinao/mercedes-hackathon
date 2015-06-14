package com.benz.dashboard.handlers;

import java.util.Map;

import com.benz.dashboard.AirTouchEmulator;
import com.benz.dashboard.FireBaseService;
import com.firebase.client.DataSnapshot;

public class LeapHandler extends BaseHandler {
	/**
	 * Lock to make sure we aren't zooming when we shouldnt be
	 */
	private long lastTime = System.currentTimeMillis();

	public static final int DURATION = 150;

	public static final int DELAY = 150;

	public LeapHandler(FireBaseService svc) {
		super(svc);
	}

	@Override
	public void handleSnapshot(DataSnapshot snapshot) {
		DataSnapshot ds = snapshot.child("leapdata");
		processLeapData(ds);
	}

	public void processLeapData(DataSnapshot ds) {
		if (System.currentTimeMillis() - DELAY < lastTime) {
			return;
		}
		lastTime = System.currentTimeMillis();

		Map<String, Object> map = (Map<String, Object>) ds.getValue();

		boolean pinch = (Boolean) map.get("pinch");
		boolean rotateCw = (Boolean) map.get("rotate_clockwise");
		boolean rotateCcw = (Boolean) map.get("rotate_counterclockwise");
		boolean swipeLeft = (Boolean) map.get("swipe_left");
		boolean swipeRight = (Boolean) map.get("swipe_right");
		boolean zoomIn = (Boolean) map.get("zoom_in");
		boolean zoomOut = (Boolean) map.get("zoom_out");

		if (zoomIn) {
			AirTouchEmulator.zoomInCentered(50, DURATION);
		} else if (zoomOut) {
			AirTouchEmulator.zoomOutCentered(50, DURATION);
		}
	}

}
