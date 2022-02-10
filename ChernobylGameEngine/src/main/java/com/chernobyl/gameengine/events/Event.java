package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventCategory;
import com.chernobyl.gameengine.events.enums.EventType;

public abstract class Event {
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
}
