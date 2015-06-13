package com.benz.dashboard;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class FireBaseService extends IntentService {

	public FireBaseService(String name) {
		super("FireBaseService");
	}

	public FireBaseService() {
		super("FireBaseService");
	}

	private Timer mBackGroundTimer = new Timer();

	@Override
	public void onCreate() {

		super.onCreate();

		Log.e("Create", "Create");

		Firebase.setAndroidContext(this);
		Firebase ref = new Firebase("https://benz.firebaseio.com");
		ref.addChildEventListener(new ChildEventListener() {
			// Retrieve new posts as they are added to the database
			@Override
			public void onChildAdded(DataSnapshot snapshot,
					String previousChildKey) {
			}

			@Override
			public void onCancelled(FirebaseError arg0) {
			}

			@Override
			public void onChildChanged(DataSnapshot snapshot, String arg1) {
				Log.e("new onChildChanged", snapshot.getValue().toString());
				if (snapshot.getKey().toString().contains("Map")) {
					CallMap(snapshot);
				} else if (snapshot.getKey().toString().contains("Web")) {
					CallWeb(snapshot);
				} else if (snapshot.getKey().toString().contains("Touch")) {
					touchEvent(snapshot);
				}

			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
			}
		});

	}

	@Override
	protected void onHandleIntent(Intent workIntent) {
		// Gets data from the incoming Intent
		// String dataString = workIntent.getDataString();

	}

	private void CallMap(DataSnapshot snapshot) {
		Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
		Log.e("new map", newPost.toString());

		Uri geoLocation = Uri.parse("geo:" + newPost.get("log") + ","
				+ newPost.get("lat") + "?q=" + newPost.get("log") + ","
				+ newPost.get("lat") + "( " + newPost.get("label") + " )");
		showMap(geoLocation);
	}

	private void CallWeb(DataSnapshot snapshot) {
		Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
		Log.e("new website", newPost.toString());
		if (newPost.get("url") != null)
			openWebPage(newPost.get("url").toString());
	}

	public void showMap(Uri geoLocation) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(geoLocation);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	public void openWebPage(String url) {
		Uri webpage = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	public void changeVolume(int streamType, int direction) {
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audio.adjustStreamVolume(streamType, direction,
				AudioManager.FLAG_ALLOW_RINGER_MODES);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	public void touchEvent(DataSnapshot snapshot) {
		int Xinput, Yinput;
		Map<String, String> newPost = (Map<String, String>) snapshot.getValue();
		Log.e("Touch", newPost.get("Xinput"));

		Xinput = (int) Long.parseLong(newPost.get("Xinput"));
		Yinput = (int) Long.parseLong(newPost.get("Yinput"));

    AirTouchEmulator.tap(Xinput, Yinput);
	}
}
