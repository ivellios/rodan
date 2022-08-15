package com.github.ivellios.rodan.services;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RodanHttpServer {

    public static HttpServer start(TasksData tasksData) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(7134), 0);
        server.createContext("/", new JiraHttpHandler(tasksData));
        server.setExecutor(null);
        server.start();
        return server;
    }
}
