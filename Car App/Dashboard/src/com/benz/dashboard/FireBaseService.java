package com.benz.dashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import com.benz.dashboard.handlers.BaseHandler;
import com.benz.dashboard.handlers.MapHandler;
import com.benz.dashboard.handlers.TouchHandler;
import com.benz.dashboard.handlers.WebHandler;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class FireBaseService extends IntentService {

	private final Map<String, BaseHandler> handlers = new HashMap<>();

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

		handlers.put("map", new MapHandler(this));
		handlers.put("touch", new TouchHandler(this));
		handlers.put("web", new WebHandler(this));

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
				String key = snapshot.getKey().toLowerCase();
				BaseHandler handler = handlers.get(key);
				if (handler == null) {
					return;
				}
				handler.handleSnapshot(snapshot);
			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
			}
		});

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

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

	}
}
