package com.chernobyl.gameengine.window;

import lombok.Getter;

public class WindowProps {
    @Getter
    final String title;
    @Getter
    final int width;
    @Getter
    final int height;

    public WindowProps() {
        title = "Chernobyl Engine";
        width = 1280;
        height = 720;
    }

    public WindowProps(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }
}
