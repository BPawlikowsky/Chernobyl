package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventType;

public class KeyReleasedEvent extends KeyEvent {
    public KeyReleasedEvent(int keyCode) {
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
