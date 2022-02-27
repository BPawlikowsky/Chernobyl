package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.Event;
import com.chernobyl.gameengine.core.input.KeyCode;

import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryInput;
import static com.chernobyl.gameengine.events.enums.EventCategory.EventCategoryKeyboard;

public abstract class KeyEvent extends Event {
    final protected KeyCode keyCode;

    public KeyEvent(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    public KeyCode GetKeyCode() {
        return keyCode;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategoryInput.getValue() | EventCategoryKeyboard.getValue();
    }
}
