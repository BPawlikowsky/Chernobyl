package com.chernobyl.client;


import com.chernobyl.gameengine.event.Event;
import com.chernobyl.gameengine.event.KeyPressedEvent;
import com.chernobyl.gameengine.event.enums.EventType;
import com.chernobyl.gameengine.input.Input;
import com.chernobyl.gameengine.layer.Layer;

import static com.chernobyl.gameengine.Log.HB_TRACE;
import static com.chernobyl.gameengine.event.enums.EventType.KeyPressed;
import static com.chernobyl.gameengine.input.KeyCodes.HB_KEY_TAB;

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
        if (Input.IsKeyPressed(HB_KEY_TAB))
            HB_TRACE("Tab key is pressed (poll)!");
    }

    @Override
    public void OnEvent(Event event) {
        if (event.GetEventType() == EventType.KeyPressed)
        {
            KeyPressedEvent e = (KeyPressedEvent) event;
            if (e.GetKeyCode() == HB_KEY_TAB)
                HB_TRACE("Tab key is pressed (event)!");
            HB_TRACE("{0}", (char)e.GetKeyCode());
        }
    }
}
