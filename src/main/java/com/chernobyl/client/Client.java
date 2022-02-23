package com.chernobyl.client;

import com.chernobyl.gameengine.core.Application;
import com.chernobyl.gameengine.EntryPoint;

public class Client extends Application {

    public static void main(String[] args) {
        Application client = Application.Create();
        EntryPoint<Application> ep = new EntryPoint<>(client);
        // client.pushLayer(new ExampleLayer());
        client.pushLayer(new Sandbox2D());
        ep.main(args);
    }
}
