package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.input.KeyCode;
import com.chernobyl.gameengine.events.enums.EventType;

public class KeyTypedEvent extends KeyEvent{
    public KeyTypedEvent(KeyCode keyCode) {
        super(keyCode);
    }

    @Override
    public EventType GetStaticType() {
        return EventType.KeyTyped;
    }

    @Override
    public String toString() {
        return "KeyTypedEvent{" +
                "keyCode=" + keyCode +
                '}';
    }
}
