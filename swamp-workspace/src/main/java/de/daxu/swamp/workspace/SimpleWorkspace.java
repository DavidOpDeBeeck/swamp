package de.daxu.swamp.workspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static de.daxu.swamp.workspace.utils.WorkspaceUtils.getAbsolutePath;
import static de.daxu.swamp.workspace.utils.WorkspaceUtils.randomName;
import static de.daxu.swamp.workspace.utils.WorkspaceValidator.isValidDirectoryName;
import static de.daxu.swamp.workspace.utils.WorkspaceValidator.isValidFileName;

public class SimpleWorkspace implements Workspace {

    private final Logger logger = LoggerFactory.getLogger(SimpleWorkspace.class);

    private final File rootDirectory;

    public SimpleWorkspace(String path) {
        this.rootDirectory = new File(path);
    }

    @Override
    public File getPath(String path) {
        return new File(getAbsolutePath(rootDirectory, path));
    }

    @Override
    public File createDirectory() {
        return createDirectory(randomName());
    }

    @Override
    public File createDirectory(String name) {
        isValidDirectoryName(name);

        String path = getAbsolutePath(rootDirectory, name);
        File directory = new File(path);

        logger.debug("Creating directory: {}", path);

        if(directory.isDirectory() || directory.mkdir())
            return directory;

        logger.debug("Failed to create directory: {}", path);

        throw new WorkspaceException("Failed to create directory: " + path);
    }

    @Override
    public File createFile(String name, String content) {
        isValidFileName(name);

        String path = getAbsolutePath(rootDirectory, name);
        File file = new File(path);

        logger.debug("Creating file: {}", path);

        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(content);
            writer.close();
        } catch(IOException e) {
            logger.error("Failed to create file: {}", path);
            throw new WorkspaceException(e);
        }

        return file;
    }

    @Override
    public File rootDirectory() {
        return rootDirectory;
    }

    @Override
    public void clear() {
        logger.debug("Removing all files in directory: {}", rootDirectory.getAbsolutePath());
        File[] files = rootDirectory.listFiles();
        if(files != null) {
            Arrays.stream(files).forEach(File::delete);
        }
    }

    @Override
    public void delete() {
        clear();
        logger.debug("Removing directory: {}", rootDirectory.getAbsolutePath());
        rootDirectory.delete();
    }
}
