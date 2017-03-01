package de.daxu.swamp.workspace;

import de.daxu.swamp.test.rule.FileSystemRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static de.daxu.swamp.test.rule.FileSystemRule.inDefaultWorkspace;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleRemovableWorkspaceTest {

    @ClassRule
    public static SpringRule spring = SpringRule.spring();
    @Rule
    public FileSystemRule fileSystem = inDefaultWorkspace(spring);

    private String workspacePath;

    @Before
    public void setUp() throws Exception {
        workspacePath = spring.getProperty("workspace.default.path");
    }

    @Test
    public void clear() throws Exception {
        SimpleRemovableWorkspace workspace = new SimpleRemovableWorkspace(workspacePath);
        File directory1 = fileSystem.createDirectory("directory1");
        File directory2 = fileSystem.createDirectory("directory2");

        workspace.clear();

        assertThat(directory1).doesNotExist();
        assertThat(directory2).doesNotExist();
    }

}