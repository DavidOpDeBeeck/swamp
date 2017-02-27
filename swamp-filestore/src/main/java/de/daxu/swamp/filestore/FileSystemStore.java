package de.daxu.swamp.filestore;

import de.daxu.swamp.filestore.extension.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class FileSystemStore implements FileStore {

    private final Logger logger = LoggerFactory.getLogger(FileSystemStore.class);

    @Value("${filestore.path}")
    private String rootPath;

    private FileSystemStore() {
    }

    @Override
    public File getDirectory(String directoryName) {
        validateDirectoryName(directoryName);

        File directory = new File(getRelativePath(directoryName));

        if(!directory.isDirectory())
            return createDirectory(directoryName);

        return directory;
    }

    @Override
    public File createDirectory(String directoryName) {
        validateDirectoryName(directoryName);

        String path = getRelativePath(directoryName);
        File directory = new File(path);

        logger.info("Creating directory: {}", path);

        if(directory.isDirectory() || directory.mkdir())
            return directory;

        logger.info("Failed to create directory: {}", path);

        throw new FileStoreException("Failed to create directory: " + path);
    }

    @Override
    public File createDirectory() {
        return createDirectory(randomName());
    }

    @Override
    public File createFile(File directory, String fileName, String content) {
        validateFileName(fileName);
        validateDirectory(directory);

        String path = getRelativePath(directory, fileName);
        File file = new File(path);

        logger.info("Creating file: {}", path);

        try {
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(content);
            writer.close();
        } catch(IOException e) {
            logger.info("Failed to create file: {}", path);
            throw new FileStoreException(e);
        }

        return file;
    }

    @Override
    public File createFile(String name, String content) {
        validateFileName(name);
        return createFile(rootDirectory(), name, content);
    }

    @Override
    public File createFile(String content) {
        return createFile(randomName(), content);
    }

    @Override
    public File createFile(File directory, String content) {
        validateDirectory(directory);
        return createFile(directory, randomName(), content);
    }

    @Override
    public File rootDirectory() {
        return new File(rootPath + File.separator);
    }

    @Override
    public File executeExtension(Extension extension) {
        try {
            return extension.execute(this);
        } catch(Exception e) {
            throw new FileStoreException("Failed to execute extension", e);
        }
    }

    private String getRelativePath(String name) {
        return getRelativePath(rootDirectory(), name);
    }

    private String getRelativePath(File directory, String name) {
        return format("%s%s%s", directory.getAbsolutePath(), File.separator, name);
    }

    private void validateFileName(String name) {
        if(isEmpty(name))
            throw new FileStoreException("Filename should not be empty");
    }

    private void validateDirectoryName(String name) {
        if(isEmpty(name))
            throw new FileStoreException("Directory name should not be empty");
    }

    private void validateDirectory(File directory) {
        if(directory == null)
            throw new FileStoreException("Directory does not exists");
        if(!directory.isDirectory())
            throw new FileStoreException("File is not a directory");
    }

    private String randomName() {
        return UUID.randomUUID().toString();
    }
}
