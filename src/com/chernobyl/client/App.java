package com.chernobyl.client;

import com.chernobyl.gameengine.EntryPoint;

public class App {
    public static void main(String[] args) {
        Client client = new Client();
        EntryPoint<Client> ep = new EntryPoint<>(client);
        ep.main(args);
    }
}
