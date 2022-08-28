package com.github.ivellios.rodan.services;

import com.github.ivellios.rodan.settings.RodanSettingsState;
import com.intellij.openapi.project.Project;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class JiraTasksService {
    private final Project project;
    private HttpServer server = null;
    private TasksData tasksData;

    private TasksPuller pullerThread;

    JiraTasksService(Project project) {
        this.project = project;
    }

    public void startService() {
        this.tasksData = new TasksData();
        this.startPuller();
        this.startServer();
    }

    public void stopService() {
        this.stopPuller();
        this.server.stop(1);
        this.server = null;

    }

    public void restartService() {
        this.stopService();
        this.startService();
    }

    public void restartPuller() {
        this.stopPuller();
        this.startPuller();
    }

    public boolean testServer() {
        startServer();
        return this.server != null;
    }

    public void startServer() {
        RodanSettingsState settings = RodanSettingsState.getInstance(this.project);
        if (this.server == null) {
            try {
                this.server = RodanHttpServer.start(this.tasksData, settings.getHttpServerPort());
            } catch (IOException exception) {
                System.out.println("Cannot start server");
                try {
                    Thread.sleep(1000);
                    this.startServer();
                } catch (InterruptedException ex) {
                    this.server = null;
                }
            }
        }
    }

    public void startPuller() {
        this.pullerThread = new TasksPuller(this.tasksData, this.project);
        new Thread(this.pullerThread).start();
    }

    public void stopPuller() {
        this.pullerThread.stopThread();
    }
}
