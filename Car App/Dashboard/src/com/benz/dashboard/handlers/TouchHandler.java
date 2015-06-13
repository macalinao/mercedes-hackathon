package com.benz.dashboard.handlers;

import java.util.Map;

import android.util.Log;

import com.benz.dashboard.AirTouchEmulator;
import com.benz.dashboard.FireBaseService;
import com.firebase.client.DataSnapshot;

public class TouchHandler extends BaseHandler {

	public TouchHandler(FireBaseService svc) {
		super(svc);
	}

	@Override
	public void handleSnapshot(DataSnapshot snapshot) {
		Map<String, String> newPost = (Map<String, String>) snapshot.getValue();
		Log.e("Touch", newPost.get("Xinput"));

		int Xinput = Integer.parseInt(newPost.get("Xinput"));
		int Yinput = Integer.parseInt(newPost.get("Yinput"));

		AirTouchEmulator.tap(Xinput, Yinput);
	}

}
