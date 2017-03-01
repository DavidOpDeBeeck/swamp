package de.daxu.swamp.workspace;

import de.daxu.swamp.test.rule.FileSystemRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static de.daxu.swamp.test.rule.FileSystemRule.inDefaultWorkspace;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkspaceManagerImplTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public FileSystemRule fileSystem = inDefaultWorkspace(spring);

    private WorkspaceManagerImpl workspaceManager;

    @Before
    public void initializeSpringDependencies() throws Exception {
        workspaceManager = spring.getInstance(WorkspaceManagerImpl.class);
    }

    @Test
    public void createWorkspace_CreatesADirectory() throws Exception {
        Workspace workspace = workspaceManager.createWorkspace();

        assertThat(workspace.rootDirectory()).exists();
    }

    @Test
    public void createRemovableWorkspace_CreatesADirectory() throws Exception {
        Workspace workspace = workspaceManager.createRemovableWorkspace();

        assertThat(workspace.rootDirectory()).exists();
    }
}