package com.benz.dashboard.handlers;

import com.firebase.client.DataSnapshot;

public abstract class BaseHandler {

  public abstract void handleSnapshot(DataSnapshot ds);
}
