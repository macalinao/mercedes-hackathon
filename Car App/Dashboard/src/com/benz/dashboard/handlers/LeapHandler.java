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

	public static final int DURATION = 500;

	public static final int DELAY = 600;

	public LeapHandler(FireBaseService svc) {
		super(svc);
	}

	@Override
	public void handleSnapshot(DataSnapshot snapshot) {
		DataSnapshot ds = snapshot.child("leapdata");
		processLeapData(ds);
	}

	public void processLeapData(DataSnapshot ds) {

		Map<String, Object> map = (Map<String, Object>) ds.getValue();

		boolean pinch = (Boolean) map.get("pinch");
		// boolean rotateCw = (Boolean) map.get("rotate_clockwise");
		// boolean rotateCcw = (Boolean) map.get("rotate_counterclockwise");
		// boolean zoomInOn = (Boolean) map.get("rotate_clockwise");
		// boolean zoomOutOn = (Boolean) map.get("rotate_counterclockwise");
		boolean swipeLeft = (Boolean) map.get("swipe_left");
		boolean swipeRight = (Boolean) map.get("swipe_right");
		Map<Integer, Object> zoomIn = (Map<Integer, Object>) map.get("zoom_in");
		Map<Integer, Object> zoomOut = (Map<Integer, Object>) map
				.get("zoom_out");

		boolean zoomInOn = (Boolean) zoomIn.get("Boolean");
		// Integer zoomInDistance = (int) zoomIn.get("Dist");
		boolean zoomOutOn = (Boolean) zoomOut.get("Boolean");
		// Integer zoomOutDistance = (int) zoomOut.get("Dist");

		if (swipeLeft) {
			new VolumeHandler(svc).changeVolume(-1);
		} else if (swipeRight) {
			new VolumeHandler(svc).changeVolume(1);
		}

		if (System.currentTimeMillis() - DELAY < lastTime) {
			return;
		}
		lastTime = System.currentTimeMillis();

		if (zoomInOn) {
			AirTouchEmulator.zoomInCentered(50, DURATION);
		} else if (zoomOutOn) {
			AirTouchEmulator.zoomOutCentered(50, DURATION);
		}
	}
}
