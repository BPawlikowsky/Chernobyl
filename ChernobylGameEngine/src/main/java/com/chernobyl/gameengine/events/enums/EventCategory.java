package com.chernobyl.gameengine.events.enums;

public enum EventCategory {
    None(0),
    EventCategoryApplication(BIT(0)),
    EventCategoryInput(BIT(1)),
    EventCategoryKeyboard(BIT(2)),
    EventCategoryMouse(BIT(3)),
    EventCategoryMouseButton(BIT(4));

    private final int value;
    public int getValue() { return value; }

    EventCategory(int cat) {
        value = cat;
    }

    private static int BIT(int cat) {
        return (1 << cat);
    }
}


