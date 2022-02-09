package com.chernobyl.gameengine;

import com.chernobyl.gameengine.events.WindowResizeEvent;

import static com.chernobyl.gameengine.Log.*;
import static com.chernobyl.gameengine.events.enums.EventCategory.*;

public abstract class Application {

    public void Run() {
        WindowResizeEvent e = new WindowResizeEvent(1280, 720);
        if (e.IsInCategory(EventCategoryApplication))
        {
            HB_TRACE(e.toString());
        }
        if (e.IsInCategory(EventCategoryInput))
        {
            HB_TRACE(e.toString());
        }
        while (true);
    }

    abstract public Application Create();
}
