package com.chernobyl.client;

import com.chernobyl.gameengine.Application;
import com.chernobyl.gameengine.EntryPoint;
import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.layer.ImGuiLayer;
import com.chernobyl.gameengine.layer.Layer;

import static com.chernobyl.gameengine.Log.HB_INFO;
import static com.chernobyl.gameengine.Log.HB_TRACE;

public class App {
    public static void main(String[] args) {
        Application client = Application.get();
        EntryPoint<Application> ep = new EntryPoint<>(client);
        client.pushLayer(new ExampleLayer());
        client.pushOverlay(new ImGuiLayer());

        ep.main(args);
    }
}

class ExampleLayer extends Layer {
    public ExampleLayer() {
        super("Example");
    }

    @Override
    public void OnAttach() {

    }

    @Override
    public void OnDetach() {

    }

    @Override
    public void OnUpdate() {
        HB_INFO("ExampleLayer::Update");
    }

    @Override
    public void OnEvent(Event event) {
        HB_INFO("{0}", event);
    }
}