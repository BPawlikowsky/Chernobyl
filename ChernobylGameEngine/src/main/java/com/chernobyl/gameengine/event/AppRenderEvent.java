package com.chernobyl.gameengine.event;

import com.chernobyl.gameengine.event.enums.EventType;

import static com.chernobyl.gameengine.event.enums.EventCategory.EventCategoryApplication;

public class AppRenderEvent extends Event{
    @Override
    public int GetCategoryFlags() {
        return EventCategoryApplication.getValue();
    }

    @Override
    EventType GetStaticType() {
        return EventType.AppRender;
    }
}
