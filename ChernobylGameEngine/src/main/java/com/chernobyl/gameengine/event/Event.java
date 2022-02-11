package com.chernobyl.gameengine.event;

import com.chernobyl.gameengine.event.enums.EventCategory;
import com.chernobyl.gameengine.event.enums.EventType;
import lombok.Getter;
import lombok.Setter;

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

    @Getter @Setter
    private boolean m_Handled = false;
}
