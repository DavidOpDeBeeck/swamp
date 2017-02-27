package de.daxu.swamp.filestore;

import de.daxu.swamp.filestore.extension.Extension;

import java.io.File;

public interface FileStore {

    File getDirectory(String name);

    File createDirectory();

    File createDirectory(String name);

    File createFile(File directory, String name, String content);

    File createFile(String content);

    File createFile(String name, String content);

    File createFile(File directory, String content);

    File rootDirectory();

    File executeExtension(Extension extension);
}
