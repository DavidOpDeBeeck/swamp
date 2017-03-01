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

public class SimpleWorkspaceTest {

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
    public void getPath() throws Exception {
        SimpleWorkspace workspace = new SimpleWorkspace(workspacePath);
        fileSystem.createDirectory("directory");

        File directory = workspace.getPath("directory");

        assertThat(directory).exists();
        assertThat(directory).hasName("directory");
    }

    @Test
    public void createDirectory() throws Exception {
        SimpleWorkspace workspace = new SimpleWorkspace(workspacePath);

        File directory = workspace.createDirectory();

        assertThat(directory).exists();
    }

    @Test
    public void createDirectoryWithName() throws Exception {
        SimpleWorkspace workspace = new SimpleWorkspace(workspacePath);

        File directory = workspace.createDirectory("directory");

        assertThat(directory).exists();
        assertThat(directory).hasName("directory");
    }

    @Test
    public void createFile() throws Exception {
        SimpleWorkspace workspace = new SimpleWorkspace(workspacePath);

        File file = workspace.createFile("file", "content");

        assertThat(file).exists();
        assertThat(file).hasName("file");
        assertThat(file).hasContent("content");
    }
}