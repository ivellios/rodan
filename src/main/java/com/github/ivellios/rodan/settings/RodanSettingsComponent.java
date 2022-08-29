package com.github.ivellios.rodan.settings;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


class TestDialogWrapper extends DialogWrapper {

  TestDialogWrapper() {
    super(true);
    setTitle("Testing service");
    init();
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    JPanel dialogPanel = new JPanel(new BorderLayout());

    JLabel label = new JLabel("Test done - ALL FINE");
    label.setPreferredSize(new Dimension(200, 50));
    dialogPanel.add(label, BorderLayout.CENTER);

    return dialogPanel;
  }

  @Override
  protected Action @NotNull [] createActions() {
    return new Action[]{getOKAction()};
  }
}


class TestServiceAction extends AbstractAction {
  TestServiceAction() {
    super("Test service");
    putValue(SHORT_DESCRIPTION, "Check if the service will run smoothly with current settings.");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    new TestDialogWrapper().show();
    System.out.println("Checking HTTP Server status!");
  }
}

class TestJiraConnectionAction extends AbstractAction {
  TestJiraConnectionAction() {
    super("Test connection to Jira");
    putValue(SHORT_DESCRIPTION, "Check if the connection to your Jira service is working properly.");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    new TestDialogWrapper().show();
    System.out.println("CHECKING JIRA!");
  }
}

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
  private final JBTextField httpServerPort = new JBTextField();

  public RodanSettingsComponent() {
    settingsPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(new JBLabel("Jira Token: "), jiraToken, 1, false)
            .addLabeledComponent(new JBLabel("Jira URL: "), jiraHostUrl, 1, false)
            .addLabeledComponent(new JBLabel("JQL to pull tasks: "), jiraQL, 1, false)
            .addLabeledComponent(new JBLabel("Refresh tasks every (ms): "), refreshEveryMs, 1, false)
            .addLabeledComponent(new JBLabel("Local HTTP Server port (set to any unused): "), httpServerPort, 1, false)
            .addLabeledComponent(new JBLabel("Jira Cloud username (fill in if you want to connect to the Jira Cloud service): "), jiraCloudUsername, 1, true)
            .addComponent(new JButton(new TestServiceAction()), 0)
            .addComponent(new JButton(new TestJiraConnectionAction()), 0)
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
  public String getHttpServerPort() {
    return httpServerPort.getText();
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

  public void setHttpServerPort(String newText) { httpServerPort.setText(newText); }

}
