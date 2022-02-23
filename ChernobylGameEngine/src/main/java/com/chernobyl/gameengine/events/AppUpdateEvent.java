package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.Event;
import com.chernobyl.gameengine.events.enums.EventType;

import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryApplication;

public class AppUpdateEvent extends Event {
    @Override
    public int GetCategoryFlags() {
        return EventCategoryApplication.getValue();
    }

    @Override
    public EventType GetStaticType() {
        return EventType.AppUpdate;
    }
}
