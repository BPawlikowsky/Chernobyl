package com.chernobyl.gameengine;

public class EntryPoint<T extends Application> {
    T ob;
    public EntryPoint(T o) {
        this.ob = o;
    }
    public void main(String[] args) {
        Application app = ob.Create();
        app.Run();
        app = null;
    }
}
