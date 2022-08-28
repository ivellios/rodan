package com.github.ivellios.rodan.listeners;

import com.github.ivellios.rodan.services.JiraTasksService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * Listener to detect project open and close.
 * Depends on {@link com.github.ivellios.rodan.services.JiraTasksService}
 */
public class JiraTasksListener implements ProjectManagerListener {
    /**
     * Invoked on project open.
     *
     * @param project opening project
     */
    @Override
    public void projectOpened(@NotNull Project project) {
        if (ApplicationManager.getApplication().isUnitTestMode()) {
            return;
        }

        JiraTasksService service = project.getService(JiraTasksService.class);
        service.startService();

    }

    /**
     * Invoked on project close.
     *
     * @param project closing project
     */
    @Override
    public void projectClosed(@NotNull Project project) {
        if (ApplicationManager.getApplication().isUnitTestMode()) {
        }
    }

    /**
     * Invoked before the project closes.
     * @param project
     */
    public void projectClosing(@NotNull Project project) {
        JiraTasksService service = project.getService(JiraTasksService.class);
        service.stopService();
    }

}