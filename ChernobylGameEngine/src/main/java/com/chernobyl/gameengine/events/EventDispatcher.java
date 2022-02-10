package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventType;

public class EventDispatcher {
    private final Event event;

    public EventDispatcher(Event e) {
        event = e;
    }

    public <T extends Event>boolean Dispatch(IEventFn<T> func, EventType eventType) {
        if (event.GetEventType() == eventType)
        {
            event.m_Handled = func.fn((T)event);
            return true;
        }
        return false;
    }
}
