package de.daxu.swamp.workspace.manager;

import de.daxu.swamp.workspace.SimpleWorkspace;
import de.daxu.swamp.workspace.Workspace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class WorkspaceManagerImpl implements WorkspaceManager {

    @Value("${workspace.default.path}")
    private String defaultWorkspacePath;
    private Workspace defaultWorkspace;

    @PostConstruct
    private void initialize() {
        this.defaultWorkspace = new SimpleWorkspace(defaultWorkspacePath);
    }

    @Override
    public Workspace createWorkspace() {
        File directory = defaultWorkspace.createDirectory();
        return new SimpleWorkspace(directory.getAbsolutePath());
    }
}
