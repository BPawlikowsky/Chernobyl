package com.chernobyl.gameengine.events;

public interface IEventFn<T extends Event> {
    boolean fn(T event);
}
