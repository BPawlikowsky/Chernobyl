package com.chernobyl.client;

import com.chernobyl.gameengine.EntryPoint;
import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.layer.Layer;

import static com.chernobyl.gameengine.Log.HB_INFO;
import static com.chernobyl.gameengine.Log.HB_TRACE;

public class App {
    public static void main(String[] args) {
        Client client = Client.Create();
        EntryPoint<Client> ep = new EntryPoint<>(client);
        client.pushOverlay(new ExampleLayer());
        ep.main(args);
    }
}

class ExampleLayer extends Layer {
    public ExampleLayer() {
        super("Example");
    }

    @Override
    protected void OnAttach() {

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