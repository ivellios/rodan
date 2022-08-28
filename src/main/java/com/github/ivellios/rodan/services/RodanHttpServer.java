package com.github.ivellios.rodan.services;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RodanHttpServer {

    public static HttpServer start(TasksData tasksData, int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new JiraHttpHandler(tasksData));
        server.setExecutor(null);
        server.start();
        return server;
    }
}
