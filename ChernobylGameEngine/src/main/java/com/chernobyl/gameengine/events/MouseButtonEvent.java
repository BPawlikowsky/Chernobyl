package com.chernobyl.gameengine.events;

import lombok.Getter;

import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryInput;
import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryMouse;

public abstract class MouseButtonEvent extends Event{
    @Getter
    protected int button;

    public MouseButtonEvent(int button) {
        this.button = button;
    }

    @Override
    public int GetCategoryFlags() {
        return (EventCategoryMouse.getValue() | EventCategoryInput.getValue());
    }
}
