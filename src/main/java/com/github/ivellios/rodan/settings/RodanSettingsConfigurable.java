package com.github.ivellios.rodan.settings;

import com.github.ivellios.rodan.services.JiraTasksService;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.tasks.TaskManager;
import com.intellij.tasks.TaskRepository;
import com.intellij.tasks.impl.TaskManagerImpl;
import com.intellij.ui.CollectionListModel;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import com.intellij.tasks.generic.GenericRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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

    JiraTasksService pullerService = this.project.getService(JiraTasksService.class);
    pullerService.restartPuller();

    TaskManagerImpl myManager = (TaskManagerImpl) TaskManager.getManager(project);
    List<TaskRepository> myRepositories = new ArrayList<>();
    CollectionListModel listModel = new CollectionListModel(new ArrayList());

    for (TaskRepository repository : myManager.getAllRepositories()) {
      TaskRepository clone = repository.clone();
      assert clone.equals(repository) : repository.getClass().getName();
      myRepositories.add(clone);
      listModel.add(clone);
    }

    TaskRepository repository = new GenericRepository();
    repository = repository.clone();
    repository.setUrl("https://google.com");

    // FIXME: Does not recognize the symbol - even tho it does for GenericRepository ebove
//    repository.setRepositoryType(com.intellij.tasks.generic.GenericRepositoryType);
    System.out.println(repository.getRepositoryType());
    System.out.println(repository.getPresentableName());
    System.out.println(repository.toString());
    // repository
    myRepositories.add(repository);
    List<TaskRepository> newRepositories = ContainerUtil.map(myRepositories, TaskRepository::clone);
    myManager.setRepositories(newRepositories);
    myManager.updateIssues(null);
  }

  @Override
  public void reset() {
    RodanSettingsState settings = RodanSettingsState.getInstance(this.project);
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
