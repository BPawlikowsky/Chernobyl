package com.chernobyl.gameengine.event;

import com.chernobyl.gameengine.event.enums.EventType;

public class KeyTypedEvent extends KeyEvent{
    public KeyTypedEvent(int keyCode) {
        super(keyCode);
    }

    @Override
    EventType GetStaticType() {
        return EventType.KeyTyped;
    }

    @Override
    public String toString() {
        return "KeyTypedEvent{" +
                "keyCode=" + keyCode +
                '}';
    }
}
