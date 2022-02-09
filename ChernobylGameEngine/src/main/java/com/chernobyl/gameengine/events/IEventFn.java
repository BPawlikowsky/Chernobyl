package com.chernobyl.gameengine.events;

public interface IEventFn<T extends Event> {
    <E> boolean fn(E event);
}
