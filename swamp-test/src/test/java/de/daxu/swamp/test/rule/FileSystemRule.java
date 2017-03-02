package de.daxu.swamp.test.rule;

import org.junit.rules.ExternalResource;

import java.io.File;
import java.util.Arrays;

public class FileSystemRule extends ExternalResource {

    public static FileSystemRule fromDefaultWorkspace(SpringRule springRule) {
        return new FileSystemRule(springRule.getProperty("workspace.default.path"));
    }

    private static String[] reservedPaths = {"C:", "/"};

    private final File rootDirectory;

    private FileSystemRule(String rootPath) {
        validatePath(rootPath);
        this.rootDirectory = new File(rootPath);
    }

    public File createDirectory(String name) {
        File directory = new File(String.format("%s/%s", rootDirectory.getAbsolutePath(), name));

        if(directory.isDirectory() || directory.mkdir())
            return directory;

        throw new RuntimeException("Failed to create directory: " + name);
    }

    @Override
    protected void before() {
        removeAllFiles();
    }

    @Override
    protected void after() {
        removeAllFiles();
    }

    private void validatePath(String path) {
        boolean reserved = Arrays.stream(reservedPaths).anyMatch(path::startsWith);
        if(reserved) {
            throw new RuntimeException(String.format("Trying to start fileSystem in a reserved path: %s", Arrays.toString(reservedPaths)));
        }
    }

    private void removeAllFiles() {
        File[] files = rootDirectory.listFiles();
        if(files != null) {
            Arrays.stream(files).forEach(File::delete);
        }
    }
}
