package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventType;

import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryApplication;

public class AppTickEvent extends Event{
    @Override
    public int GetCategoryFlags() {
        return EventCategoryApplication.getValue();
    }

    @Override
    EventType GetStaticType() {
        return EventType.AppTick;
    }
}
