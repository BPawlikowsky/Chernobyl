package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.Event;
import com.chernobyl.gameengine.events.enums.EventCategory;
import com.chernobyl.gameengine.events.enums.EventType;

public class WindowCloseEvent  extends Event {
    @Override
    public int GetCategoryFlags() {
        return EventCategory.EventCategoryApplication.getValue();
    }

    @Override
    public EventType GetStaticType() {
        return EventType.WindowClose;
    }
}
