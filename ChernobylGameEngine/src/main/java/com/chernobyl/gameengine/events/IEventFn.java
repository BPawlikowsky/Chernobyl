package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.Event;

public interface IEventFn<T extends Event> {
    boolean fn(T event);
}
