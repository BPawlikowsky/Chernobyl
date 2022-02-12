package com.chernobyl.gameengine.event;

import static com.chernobyl.gameengine.event.enums.EventCategory.EventCategoryInput;
import static com.chernobyl.gameengine.event.enums.EventCategory.EventCategoryKeyboard;

public abstract class KeyEvent extends Event {
    final protected int keyCode;

    public KeyEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int GetKeyCode() {
        return keyCode;
    }

    @Override
    public int GetCategoryFlags() {
        return EventCategoryInput.getValue() | EventCategoryKeyboard.getValue();
    }
}
