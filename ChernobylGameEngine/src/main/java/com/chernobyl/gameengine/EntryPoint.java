package com.chernobyl.gameengine;

import com.chernobyl.gameengine.core.Application;

import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_BEGIN_SESSION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_END_SESSION;

public class EntryPoint<T extends Application> {
    final T client;
    public EntryPoint(T o) {
        this.client = o;
    }

    public void main(String[] args) {
        HB_PROFILE_BEGIN_SESSION("Runtime", "HazelProfile-Runtime.json");
        client.Run();
        HB_PROFILE_END_SESSION();
    }
}
