package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.events.enums.EventType;

public class KeyTypedEvent extends KeyEvent{
    public KeyTypedEvent(int keyCode) {
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
