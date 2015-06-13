package com.benz.dashboard.handlers;

import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.benz.dashboard.FireBaseService;
import com.firebase.client.DataSnapshot;

public class MapHandler extends BaseHandler {

	public MapHandler(FireBaseService svc) {
		super(svc);
	}

	@Override
	public void handleSnapshot(DataSnapshot snapshot) {
		Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
		Log.e("new map", newPost.toString());

		Uri geoLocation = Uri.parse("geo:" + newPost.get("log") + ","
				+ newPost.get("lat") + "?q=" + newPost.get("log") + ","
				+ newPost.get("lat") + "( " + newPost.get("label") + " )");
		showMap(geoLocation);
	}

	public void showMap(Uri geoLocation) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(geoLocation);
		if (intent.resolveActivity(svc.getPackageManager()) != null) {
			svc.startActivity(intent);
		}
	}
}
