package com.chernobyl.gameengine;

public class EntryPoint<T extends Application> {
    final T client;
    public EntryPoint(T o) {
        this.client = o;
    }

    public void main(String[] args) {
        client.Run();
    }
}
