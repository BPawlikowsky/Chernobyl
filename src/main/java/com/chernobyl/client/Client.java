package com.chernobyl.client;

import com.chernobyl.gameengine.Application;

public class Client extends Application {
    private static Client client = null;
    private Client() {
        super();
    }

    public static Client Create() {
        if(Client.client == null) {
            Client.client = new Client();
        }
        return Client.client;
    }
}
