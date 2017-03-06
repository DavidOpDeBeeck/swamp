package de.daxu.swamp.workspace.manager;

import de.daxu.swamp.workspace.Workspace;

import java.util.function.Consumer;

public interface WorkspaceManager {

    Workspace createWorkspace();

    default void doInWorkspace(Consumer<Workspace> workspaceConsumer) {
        Workspace workspace = createWorkspace();
        workspaceConsumer.accept(workspace);
        workspace.delete();
    }

}
