package com.chernobyl.gameengine;

import com.chernobyl.gameengine.core.Application;

public class EntryPoint<T extends Application> {
    final T client;
    public EntryPoint(T o) {
        this.client = o;
    }

    public void main(String[] args) {
        client.Run();
    }
}
