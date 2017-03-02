package de.daxu.swamp.workspace.manager;

import de.daxu.swamp.test.rule.SpringRule;
import de.daxu.swamp.workspace.Workspace;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkspaceManagerImplTest {

    @ClassRule
    public static SpringRule spring = spring();

    private WorkspaceManager workspaceManager;

    @Before
    public void setUp() throws Exception {
        workspaceManager = spring.getInstance(WorkspaceManagerImpl.class);
    }

    @Test
    public void createWorkspace_CreatesADirectory() throws Exception {
        Workspace workspace = workspaceManager.createWorkspace();

        assertThat(workspace.rootDirectory()).exists();
    }
}