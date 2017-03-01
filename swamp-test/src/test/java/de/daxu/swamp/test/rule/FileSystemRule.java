package de.daxu.swamp.test.rule;

import org.junit.rules.ExternalResource;

import java.io.File;
import java.util.Arrays;

public class FileSystemRule extends ExternalResource {

    public static FileSystemRule inDefaultWorkspace(SpringRule spring) {
        return new FileSystemRule(spring.getProperty("workspace.default.path"));
    }

    private static String[] reservedPaths = {"C:", "/"};

    private final File rootDirectory;

    private FileSystemRule(String path) {
        validatePath(path);
        this.rootDirectory = new File(path);
    }

    public File createDirectory(String name) {
        File directory = new File(String.format("%s/%s", rootDirectory.getAbsolutePath(), name));

        if(directory.isDirectory() || directory.mkdir())
            return directory;

        throw new RuntimeException("Failed to create directory: " + name);
    }

    private void validatePath(String path) {
        boolean reserved = Arrays.stream(reservedPaths).anyMatch(path::startsWith);
        if(reserved) {
            throw new RuntimeException(String.format("FileSystem starts with a reserved path: %s", Arrays.toString(reservedPaths)));
        }
    }

    @Override
    protected void before() {
        removeAllFilesInFileSystem();
    }

    @Override
    protected void after() {
        removeAllFilesInFileSystem();
    }

    private void removeAllFilesInFileSystem() {
        File[] filesInWorkspace = rootDirectory.listFiles();
        if(filesInWorkspace != null) {
            Arrays.stream(filesInWorkspace).forEach(File::delete);
        }
    }
}
