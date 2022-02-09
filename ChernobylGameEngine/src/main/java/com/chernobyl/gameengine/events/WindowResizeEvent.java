package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventType;
import lombok.Getter;

import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryApplication;

public class WindowResizeEvent extends Event{
    @Getter
    private final int width;
    @Getter
    private final int height;

    public WindowResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategoryApplication.getValue();
    }

    @Override
    EventType GetStaticType() {
        return EventType.WindowResize;
    }

    @Override
    public String toString() {
        return "WindowResizeEvent{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
