package com.chernobyl.gameengine.window;

import com.chernobyl.gameengine.events.Event;

public interface IEventCallback {
    void invoke(Event event);
}
