package com.benz.dashboard.handlers;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;

import com.benz.dashboard.FireBaseService;
import com.firebase.client.DataSnapshot;

public class VolumeHandler extends BaseHandler {

	private AudioManager audio;
	private SharedPreferences mPreference;
	private static final String DASHBOARD_PREFERENCE = "dashboard_preferenec";
	private static final String SYSTEM_STREAM_VOLUME = "system_stream_volum";

	public VolumeHandler(FireBaseService svc) {
		super(svc);
		audio = (AudioManager) svc.getSystemService(Context.AUDIO_SERVICE);
		mPreference = svc.getSharedPreferences(DASHBOARD_PREFERENCE, Context.MODE_PRIVATE);
	}

	@Override
	public void handleSnapshot(DataSnapshot snapshot) {
		if(snapshot == null) return;
		
		Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
		int volume = (int) newPost.get("volume");
		
		changeVolume(volume);
	}

	public void changeVolume(int direction) {
		int currentVolume = audio.getStreamVolume(AudioManager.STREAM_SYSTEM);
		audio.setStreamVolume(AudioManager.STREAM_SYSTEM, currentVolume + direction, AudioManager.FLAG_SHOW_UI);
	}

	protected void muteSystem(DataSnapshot snapshot) {
		if (snapshot == null) return;
		Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
		boolean mute = (boolean) newPost.get("mute");
		if (mute) {
			int streamVolume = audio.getStreamVolume(AudioManager.STREAM_SYSTEM);
			mPreference.edit().putInt(SYSTEM_STREAM_VOLUME, streamVolume).commit();
			// changeVolume(0);
			audio.setStreamMute(AudioManager.STREAM_SYSTEM, mute);
		} else {
			int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
			int saveVolume = mPreference.getInt(SYSTEM_STREAM_VOLUME, maxVolume / 2);
			audio.setStreamVolume(AudioManager.STREAM_SYSTEM, saveVolume, AudioManager.FLAG_SHOW_UI);
			// audio.setStreamMute(AudioManager.STREAM_SYSTEM, mute);
		}
	}
}