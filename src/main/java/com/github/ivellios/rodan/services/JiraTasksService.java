package com.github.ivellios.rodan.services;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class JiraTasksService {

    private HttpServer server = null;
    private TasksData tasksData;

    private TasksPuller pullerThread;

    public void startService() {
        this.tasksData = new TasksData();
        this.startPuller();
        this.startServer();
    }

    public void restartPuller() {
        this.stopPuller();
        this.startPuller();
    }

    public void startServer() {
        if (server == null) {
            try {
                this.server = RodanHttpServer.start(this.tasksData);
            } catch (IOException exception) {
                System.out.println("Cannot start server");
            }
        }
    }

    public void startPuller() {
        this.pullerThread = new TasksPuller(this.tasksData);
        new Thread(this.pullerThread).start();
    }

    public void stopPuller() {
        this.pullerThread.stopThread();
    }
}
