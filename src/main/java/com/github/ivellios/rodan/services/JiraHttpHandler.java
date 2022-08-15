package com.github.ivellios.rodan.services;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

class JiraHttpHandler implements HttpHandler {

    TasksData tasksData;

    JiraHttpHandler(TasksData tasksData) {
        super();
        this.tasksData = tasksData;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        exchange.getResponseHeaders().set("Content-type", "application/json");
        String response = this.tasksData.json;
        try {
            if(response == null) {
                exchange.sendResponseHeaders(404, 0);
            } else {
                exchange.sendResponseHeaders(200, response.getBytes().length);
                outputStream.write(response.getBytes());
            }
        }
        finally {
            outputStream.flush();
            outputStream.close();
            exchange.close();
        }
    }
}
