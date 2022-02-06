package com.chernobyl.gameengine;

import org.slf4j.Logger;

public class EntryPoint<T extends Application> {
    T client;
    public EntryPoint(T o) {
        this.client = o;
    }

    public void main(String[] args) {
        Application app = client.Create();

        Log.getCoreLogger().warn("Initialized log!");
        Log.getClientLogger().info("Hello!");

        app.Run();
        app = null;
    }
}
