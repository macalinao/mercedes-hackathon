package com.benz.dashboard.handlers;

import com.benz.dashboard.FireBaseService;
import com.firebase.client.DataSnapshot;

public abstract class BaseHandler {
	protected FireBaseService svc;

	protected BaseHandler(FireBaseService svc) {
		this.svc = svc;
	}

	public abstract void handleSnapshot(DataSnapshot ds);
}
