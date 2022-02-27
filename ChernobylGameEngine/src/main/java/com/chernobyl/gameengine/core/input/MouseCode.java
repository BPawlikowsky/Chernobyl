package com.chernobyl.gameengine.core.input;

public enum MouseCode {
    Button0                (0),
    Button1                (1),
    Button2                (2),
    Button3                (3),
    Button4                (4),
    Button5                (5),
    Button6                (6),
    Button7                (7),

    ButtonLast             (7),
    ButtonLeft             (0),
    ButtonRight            (1),
    ButtonMiddle           (2);
    private final int keycode;
    MouseCode(int key) {
        keycode = key;
    }

    public int key() {
        return keycode;
    }

    public static MouseCode getEnumFromKey(int key) {
        for (var e :
                MouseCode.values()) {
            if (e.key() == key)
                return e;
        }
        return MouseCode.ButtonLast;
    }
}
