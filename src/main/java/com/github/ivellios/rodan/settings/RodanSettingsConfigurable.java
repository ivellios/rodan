package com.github.ivellios.rodan.settings;

import com.github.ivellios.rodan.services.JiraTasksService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RodanSettingsConfigurable implements Configurable {

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
    RodanSettingsState settings = RodanSettingsState.getInstance();
    boolean modified = !settingsComponent.getJiraToken().equals(settings.jiraToken);
    modified |= !settingsComponent.getJiraHostUrl().equals(settings.jiraHostUrl);
    modified |= !settingsComponent.getJiraQL().equals(settings.jiraQL);
    modified |= !settingsComponent.getRefreshEveryMs().equals(settings.refreshEveryMs);
    modified |= !settingsComponent.getJiraCloudUsername().equals(settings.jiraCloudUsername);
    return modified;
  }

  @Override
  public void apply() {
    RodanSettingsState settings = RodanSettingsState.getInstance();
    settings.jiraToken = settingsComponent.getJiraToken();
    settings.jiraHostUrl = settingsComponent.getJiraHostUrl();
    settings.jiraQL = settingsComponent.getJiraQL();
    settings.refreshEveryMs = settingsComponent.getRefreshEveryMs();
    settings.jiraCloudUsername = settingsComponent.getJiraCloudUsername();

    JiraTasksService projectCountingService =
            ApplicationManager.getApplication().getService(JiraTasksService.class);

    projectCountingService.restartPuller();
  }

  @Override
  public void reset() {
    RodanSettingsState settings = RodanSettingsState.getInstance();
    settingsComponent.setJiraTokenText(settings.jiraToken);
    settingsComponent.setJiraHostUrlText(settings.jiraHostUrl);
    settingsComponent.setJiraQLText(settings.jiraQL);
    settingsComponent.setRefreshEveryMs(settings.refreshEveryMs);
    settingsComponent.setJiraCloudUsername(settings.jiraCloudUsername);
  }

  @Override
  public void disposeUIResources() {
    settingsComponent = null;
  }

}
