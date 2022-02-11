package com.chernobyl.gameengine.event;

public interface IEventFn<T extends Event> {
    boolean fn(T event);
}
