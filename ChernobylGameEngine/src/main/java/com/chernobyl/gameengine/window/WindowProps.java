package com.chernobyl.gameengine.window;

import lombok.Getter;

public class WindowProps {
    @Getter
    String title;
    @Getter
    int width;
    @Getter
    int height;

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
