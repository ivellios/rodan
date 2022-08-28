package com.github.ivellios.rodan.settings;

import com.github.ivellios.rodan.services.JiraTasksService;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RodanSettingsConfigurable implements Configurable {
  private final Project project;

  RodanSettingsConfigurable(Project project) {
    this.project = project;
  }

  private RodanSettingsComponent settingsComponent;

  @Nls(capitalization = Nls.Capitalization.Title)
  @Override
  public String getDisplayName() {
    return "Rodan";
  }

  @Override
  public JComponent getPreferredFocusedComponent() {
    return settingsComponent.getPreferredFocusedComponent();
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    settingsComponent = new RodanSettingsComponent();
    return settingsComponent.getPanel();
  }

  @Override
  public boolean isModified() {
    RodanSettingsState settings = RodanSettingsState.getInstance(this.project);
    boolean modified = !settingsComponent.getJiraToken().equals(settings.jiraToken);
    modified |= !settingsComponent.getJiraHostUrl().equals(settings.jiraHostUrl);
    modified |= !settingsComponent.getJiraQL().equals(settings.jiraQL);
    modified |= !settingsComponent.getRefreshEveryMs().equals(settings.refreshEveryMs);
    modified |= !settingsComponent.getJiraCloudUsername().equals(settings.jiraCloudUsername);
    modified |= !settingsComponent.getHttpServerPort().equals(settings.httpServerPort);
    return modified;
  }

  @Override
  public void apply() {
    RodanSettingsState settings = RodanSettingsState.getInstance(this.project);
    settings.jiraToken = settingsComponent.getJiraToken();
    settings.jiraHostUrl = settingsComponent.getJiraHostUrl();
    settings.jiraQL = settingsComponent.getJiraQL();
    settings.refreshEveryMs = settingsComponent.getRefreshEveryMs();
    settings.jiraCloudUsername = settingsComponent.getJiraCloudUsername();
    settings.httpServerPort = settingsComponent.getHttpServerPort();

    JiraTasksService tasksService = this.project.getService(JiraTasksService.class);
    System.out.println("Restarting");
    tasksService.restartService();
  }

  @Override
  public void reset() {
    RodanSettingsState settings = RodanSettingsState.getInstance(this.project);
    settingsComponent.setJiraTokenText(settings.jiraToken);
    settingsComponent.setJiraHostUrlText(settings.jiraHostUrl);
    settingsComponent.setJiraQLText(settings.jiraQL);
    settingsComponent.setRefreshEveryMs(settings.refreshEveryMs);
    settingsComponent.setJiraCloudUsername(settings.jiraCloudUsername);
    settingsComponent.setHttpServerPort(settings.httpServerPort);
  }

  @Override
  public void disposeUIResources() {
    settingsComponent = null;
  }

}
