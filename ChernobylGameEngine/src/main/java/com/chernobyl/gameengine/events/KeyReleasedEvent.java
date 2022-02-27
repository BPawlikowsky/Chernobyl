package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.input.KeyCode;
import com.chernobyl.gameengine.events.enums.EventType;

public class KeyReleasedEvent extends KeyEvent {
    public KeyReleasedEvent(KeyCode keyCode) {
        super(keyCode);
    }

    @Override
    public EventType GetStaticType() {
        return EventType.KeyReleased;
    }

    @Override
    public String toString() {
        return "KeyReleasedEvent{" +
                "keyCode=" + keyCode +
                '}';
    }
}
