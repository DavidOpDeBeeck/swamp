package de.daxu.swamp.filestore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import static java.lang.String.format;

@Component
public class LocalFileStore implements FileStore {

    @Value("${filestore.path}")
    private String rootPath;

    private LocalFileStore() {
    }

    @Override
    public File createDirectory(String name) {
        String path = format("%s%s%s", rootPath, File.separator, name);
        File directory = new File(path);

        if(directory.mkdir())
            return directory;

        throw new RuntimeException("Failed to create directory: " + path);
    }

    @Override
    public File createTempDirectory() {
        String name = UUID.randomUUID().toString();
        return createDirectory(name);
    }

    @Override
    public File createFile(File directory, String name, String content) {
        String path = format("%s%s%s", directory.getAbsolutePath(), File.separator, name);
        File file = new File(path);

        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(content);
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    @Override
    public File createFile(String name, String content) {
        return createFile(rootDirectory(), name, content);
    }

    @Override
    public File createTempFile(String content) {
        String name = UUID.randomUUID().toString();
        return createFile(name, content);
    }

    @Override
    public File createTempFile(File directory, String content) {
        String name = UUID.randomUUID().toString();
        return createFile(directory, name, content);
    }

    private File rootDirectory() {
        return new File(rootPath + File.separator);
    }
}
