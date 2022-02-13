package com.chernobyl.gameengine.renderer;

import lombok.Getter;

public enum RendererAPI {
    None(0), OpenGL(1);

    @Getter
    private final int option;

    RendererAPI(int i) {
        option = i;
    }
}
