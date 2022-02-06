package com.chernobyl.gameengine;

public class EntryPoint<T extends Application> {
    T client;
    public EntryPoint(T o) {
        this.client = o;
    }

    public void main(String[] args) {
        Application app = client.Create();
        app.Run();
        app = null;
    }
}
