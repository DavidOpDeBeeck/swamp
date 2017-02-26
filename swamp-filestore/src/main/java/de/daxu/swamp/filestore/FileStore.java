package de.daxu.swamp.filestore;

import java.io.File;

public interface FileStore {

    File createDirectory(String name);

    File createTempDirectory();

    File createFile(File directory, String name, String content);

    File createFile(String name, String content);

    File createTempFile(String content);

    File createTempFile(File directory, String content);
}
