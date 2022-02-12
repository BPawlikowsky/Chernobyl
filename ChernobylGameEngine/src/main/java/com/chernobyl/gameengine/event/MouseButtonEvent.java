package com.chernobyl.gameengine.event;

import lombok.Getter;

import static com.chernobyl.gameengine.event.enums.EventCategory.EventCategoryInput;
import static com.chernobyl.gameengine.event.enums.EventCategory.EventCategoryMouse;

public abstract class MouseButtonEvent extends Event{
    @Getter
    protected int mouseButton;

    public MouseButtonEvent(int mouseButton) {
        this.mouseButton = mouseButton;
    }

    @Override
    public int GetCategoryFlags() {
        return (EventCategoryMouse.getValue() | EventCategoryInput.getValue());
    }
}
