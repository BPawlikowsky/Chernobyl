package com.chernobyl.client;

import com.chernobyl.gameengine.Application;
import com.chernobyl.gameengine.EntryPoint;

public class App {
    public static void main(String[] args) {
        Application client = Application.get();
        EntryPoint<Application> ep = new EntryPoint<>(client);
        client.pushLayer(new ExampleLayer());

        ep.main(args);
    }
}
