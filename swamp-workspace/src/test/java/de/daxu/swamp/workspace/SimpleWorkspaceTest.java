package de.daxu.swamp.workspace;

import de.daxu.swamp.test.rule.FileSystemRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static de.daxu.swamp.test.rule.FileSystemRule.fromDefaultWorkspace;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleWorkspaceTest {

    private static final String TEST_DIRECTORY = "SimpleWorkspaceTest";

    @ClassRule
    public static SpringRule spring = SpringRule.spring();
    @Rule
    public FileSystemRule fileSystem = fromDefaultWorkspace(spring);

    private Workspace workspace;
    private File workspaceRoot;

    @Before
    public void setUp() throws Exception {
        workspaceRoot = fileSystem.createDirectory(TEST_DIRECTORY);
        workspace = new SimpleWorkspace(workspaceRoot.getAbsolutePath());
    }

    @Test
    public void getPath() throws Exception {
        fileSystem.createDirectory(TEST_DIRECTORY + "/directory");

        File directory = workspace.getPath("directory");

        assertThat(directory).exists();
        assertThat(directory).hasName("directory");
    }

    @Test
    public void createDirectory() throws Exception {
        File directory = workspace.createDirectory();

        assertThat(directory).exists();
    }

    @Test
    public void createDirectoryWithName() throws Exception {
        File directory = workspace.createDirectory("directory");

        assertThat(directory).exists();
        assertThat(directory).hasName("directory");
    }

    @Test
    public void createFile() throws Exception {
        File file = workspace.createFile("file", "content");

        assertThat(file).exists();
        assertThat(file).hasName("file");
        assertThat(file).hasContent("content");
    }

    @Test
    public void clear() throws Exception {
        File directory1 = fileSystem.createDirectory(TEST_DIRECTORY + "/directory1");
        File directory2 = fileSystem.createDirectory(TEST_DIRECTORY + "/directory2");

        workspace.clear();

        assertThat(directory1).doesNotExist();
        assertThat(directory2).doesNotExist();
    }

    @Test
    public void delete() throws Exception {
        String workspaceRootPath = workspaceRoot.getAbsolutePath();

        workspace.delete();

        assertThat(new File(workspaceRootPath)).doesNotExist();
    }
}