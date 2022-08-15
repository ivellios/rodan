package com.github.ivellios.rodan.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Stores persistent data with the plugin settings.
 */
@State(
        name = "org.intellij.sdk.settings.RodanSettingsState",
        storages = @Storage("RodanSettingsPlugin.xml")
)
public class RodanSettingsState implements PersistentStateComponent<RodanSettingsState> {
  public String jiraToken = "";
  public String jiraHostUrl = "";
  public String jiraQL = "assignee%20%3D%20currentUser%28%29%20AND%20resolution%20%3D%20unresolved"; // default value for active user tasks
  public String jiraCloudUsername = "";
  public String refreshEveryMs = "5000";

  public static RodanSettingsState getInstance() {
    return ApplicationManager.getApplication().getService(RodanSettingsState.class);
  }

  @Nullable
  @Override
  public RodanSettingsState getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull RodanSettingsState state) {
    XmlSerializerUtil.copyBean(state, this);
  }

}
