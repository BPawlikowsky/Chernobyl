package com.chernobyl.gameengine.window;

import com.chernobyl.gameengine.event.Event;

public interface IEventCallback {
    void invoke(Event event);
}
