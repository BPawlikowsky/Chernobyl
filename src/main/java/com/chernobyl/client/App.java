package com.chernobyl.client;

import com.chernobyl.gameengine.Application;
import com.chernobyl.gameengine.EntryPoint;
import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.input.Input;
import com.chernobyl.gameengine.layer.ImGuiLayer;
import com.chernobyl.gameengine.layer.Layer;

public class App {
    public static void main(String[] args) {
        Application client = Application.get();
        EntryPoint<Application> ep = new EntryPoint<>(client);
        client.pushLayer(new ExampleLayer());
        client.pushOverlay(new ImGuiLayer());

        ep.main(args);
    }
}
