package com.chernobyl.gameengine.events;

import com.chernobyl.gameengine.core.input.KeyCode;
import com.chernobyl.gameengine.events.enums.EventType;
import lombok.Getter;

public class KeyPressedEvent extends KeyEvent {
    @Getter
    private int repeatCount;

    public KeyPressedEvent(KeyCode keyCode) {
        super(keyCode);
    }

    public KeyPressedEvent(KeyCode keyCode, int repeatCount) {
        super(keyCode);
        this.repeatCount = repeatCount;
    }

    @Override
    public EventType GetStaticType() {
        return EventType.KeyPressed;
    }

    @Override
    public String toString() {
        return "KeyPressedEvent{" +
                "keyCode=" + keyCode +
                ", repeatCount=" + repeatCount +
                '}';
    }
}
