package com.chernobyl.gameengine.event;

import com.chernobyl.gameengine.event.enums.EventType;

public class KeyReleasedEvent extends KeyEvent {
    public KeyReleasedEvent(int keyCode) {
        super(keyCode);
    }

    @Override
    EventType GetStaticType() {
        return EventType.KeyReleased;
    }

    @Override
    public String toString() {
        return "KeyReleasedEvent{" +
                "keyCode=" + keyCode +
                '}';
    }
}
