package com.github.ivellios.rodan.settings;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class RodanSettingsComponent {

  private final JPanel settingsPanel;
  private final JBPasswordField jiraToken = new JBPasswordField();
  private final JBTextField jiraHostUrl = new JBTextField();
  private final JBTextField jiraQL = new JBTextField();
  private final JBTextField refreshEveryMs = new JBTextField();
  private final JBTextField jiraCloudUsername = new JBTextField();

  public RodanSettingsComponent() {
    settingsPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(new JBLabel("Jira Token: "), jiraToken, 1, false)
            .addLabeledComponent(new JBLabel("Jira URL: "), jiraHostUrl, 1, false)
            .addLabeledComponent(new JBLabel("JQL to pull tasks: "), jiraQL, 1, false)
            .addLabeledComponent(new JBLabel("Refresh tasks every (ms): "), refreshEveryMs, 1, false)
            .addLabeledComponent(new JBLabel("Jira Cloud username (fill in if you want to connect to the Jira Cloud service): "), jiraCloudUsername, 1, true)
            .addComponentFillVertically(new JPanel(), 0)
            .getPanel();
  }

  public JPanel getPanel() {
    return settingsPanel;
  }

  public JComponent getPreferredFocusedComponent() {
    return jiraToken;
  }

  @NotNull
  public String getJiraToken() {
    return jiraToken.getText();
  }

  @NotNull
  public String getJiraHostUrl() {
    return jiraHostUrl.getText();
  }

  @NotNull
  public String getJiraQL() {
    return jiraQL.getText();
  }

  @NotNull
  public String getRefreshEveryMs() {
    return refreshEveryMs.getText();
  }

  public String getJiraCloudUsername() {
    return jiraCloudUsername.getText();
  }

  public void setJiraTokenText(@NotNull String newText) {
    jiraToken.setText(newText);
  }

  public void setJiraHostUrlText(@NotNull String newText) {
    jiraHostUrl.setText(newText);
  }

  public void setJiraQLText(@NotNull String newText) {
    jiraQL.setText(newText);
  }

  public void setRefreshEveryMs(@NotNull String newText) {
    refreshEveryMs.setText(newText);
  }

  public void setJiraCloudUsername(String newText) { jiraCloudUsername.setText(newText); }

}
