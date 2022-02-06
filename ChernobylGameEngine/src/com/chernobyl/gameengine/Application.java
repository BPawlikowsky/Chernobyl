package com.chernobyl.gameengine;

public abstract class Application {
    public void Run() {
        while (true);
    }

    abstract public Application Create();
}
