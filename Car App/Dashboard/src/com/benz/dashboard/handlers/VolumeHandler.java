package com.benz.dashboard.handlers;

import android.content.Context;
import android.media.AudioManager;

import com.benz.dashboard.FireBaseService;
import com.firebase.client.DataSnapshot;

public class VolumeHandler extends BaseHandler {

	protected VolumeHandler(FireBaseService svc) {
		super(svc);
	}

	@Override
	public void handleSnapshot(DataSnapshot snapshot) {

	}

	public void changeVolume(int streamType, int direction) {
		AudioManager audio = (AudioManager) svc
				.getSystemService(Context.AUDIO_SERVICE);
		audio.adjustStreamVolume(streamType, direction,
				AudioManager.FLAG_ALLOW_RINGER_MODES);
	}

}
