package com.benz.dashboard.handlers;

import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.benz.dashboard.FireBaseService;
import com.benz.dashboard.BrightNess;
import com.firebase.client.DataSnapshot;

public class WebHandler extends BaseHandler {

	public WebHandler(FireBaseService svc) {
		super(svc);
	}

	@Override
	public void handleSnapshot(DataSnapshot snapshot) {
		Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
		Log.e("new website", newPost.toString());
		if (newPost.get("url") != null)
			openWebPage(newPost.get("url").toString());
	}

	public void openWebPage(String url) {
		Uri webpage = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (intent.resolveActivity(svc.getPackageManager()) != null) {
			svc.startActivity(intent);
		}
		if (url.contains("youtubu")) {
			BrightNess instance = BrightNess.getInstance(svc);
			instance.setSystemBrightness(BrightNess.BLACK_SCREEN);
		}
	}

}
