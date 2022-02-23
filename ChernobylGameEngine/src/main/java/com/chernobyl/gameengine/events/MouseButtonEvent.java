package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.Event;
import lombok.Getter;

import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryInput;
import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryMouse;

public abstract class MouseButtonEvent extends Event {
    @Getter
    final protected int mouseButton;

    public MouseButtonEvent(int mouseButton) {
        this.mouseButton = mouseButton;
    }

    @Override
    public int GetCategoryFlags() {
        return (EventCategoryMouse.getValue() | EventCategoryInput.getValue());
    }
}
