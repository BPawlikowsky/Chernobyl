package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventCategory;
import com.chernobyl.gameengine.events.enums.EventType;

abstract class Event {
    protected EventType eventType = GetStaticType();

    public EventType GetEventType() { return GetStaticType(); }

    public String GetName() { return GetStaticType().name(); }

    public abstract int GetCategoryFlags();

    abstract EventType GetStaticType();

    public String ToString() {
        return String.valueOf(GetName());
    }

    public boolean IsInCategory(EventCategory category) {
        return (GetCategoryFlags() & category.getValue()) > 0;
    }

    boolean m_Handled = false;

    public class EventDispatcher {
        private final Event event;

        public EventDispatcher(Event e) {
            event = e;
        }

        <T extends Event>boolean Dispatch(IEventFn<T> func) {
            if (event.GetEventType() == eventType)
            {
                event.m_Handled = func.fn(event);
                return true;
            }
            return false;
        }
    }
}
