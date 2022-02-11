package com.chernobyl.gameengine.event;

import com.chernobyl.gameengine.event.enums.EventType;
import lombok.Getter;

public class KeyPressedEvent extends KeyEvent {
    @Getter
    private int repeatCount;

    public KeyPressedEvent(int keyCode) {
        super(keyCode);
    }

    public KeyPressedEvent(int keyCode, int repeatCount) {
        super(keyCode);
        this.repeatCount = repeatCount;
    }

    @Override
    EventType GetStaticType() {
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
