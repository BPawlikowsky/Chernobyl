package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventType;

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
        return eventType.name() + ":" +
                ", keyCode=" + keyCode +
                "|---";
    }
}
