package com.github.ivellios.rodan.services;

import com.github.ivellios.rodan.settings.RodanSettingsState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

class TasksPuller implements Runnable {
    private volatile boolean isRunning = true;

    private TasksData tasksData;
    private String jiraApiVersion = "2";
    RodanSettingsState settings;

    TasksPuller(TasksData tasksData) {
        super();
        this.tasksData = tasksData;
        loadSettings();
    }

    private void loadSettings() {
        settings = RodanSettingsState.getInstance();
    }

    private String getToken() {
        if(!settings.jiraCloudUsername.equals("")) {
            String composedToken = String.format("%s:%s", settings.jiraCloudUsername, settings.jiraToken);
            String encodedToken = Base64.getEncoder().encodeToString(composedToken.getBytes());
            return String.format("Basic %s", encodedToken);
        } else {
            return String.format("Bearer %s", settings.jiraToken);
        }
    }

    private String getData() {
        try {
            URL url = new URL(String.format("%s/rest/api/%s/search?jql=%s", settings.jiraHostUrl, jiraApiVersion, settings.jiraQL));
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", this.getToken());

            int status = connection.getResponseCode();

            if(status != 200) {
                return null;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return content.toString();

        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                this.tasksData.json = this.getData();
                Thread.sleep(Integer.parseInt(settings.refreshEveryMs));
            }
        }
        catch (InterruptedException exception) {}
    }

    public void stopThread() {
        this.isRunning = false;
    }

}