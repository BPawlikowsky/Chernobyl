package com.chernobyl.client;

import com.chernobyl.gameengine.core.Application;
import com.chernobyl.gameengine.EntryPoint;

import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_BEGIN_SESSION;
import static com.chernobyl.gameengine.core.Instrumentor.HB_PROFILE_END_SESSION;

public class Client extends Application {

    public static void main(String[] args) {
        HB_PROFILE_BEGIN_SESSION("Startup", "HazelProfile-Startup.json");
        Application client = Application.Create();

        client.pushLayer(new Sandbox2D());
        EntryPoint<Application> ep = new EntryPoint<>(client);
        HB_PROFILE_END_SESSION();

        ep.main(args);
    }
}
