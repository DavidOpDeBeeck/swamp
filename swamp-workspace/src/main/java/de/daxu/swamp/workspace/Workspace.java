package de.daxu.swamp.workspace;

import de.daxu.swamp.workspace.extension.Extension;

import java.io.File;

public interface Workspace {

    File getPath(String path);

    File createDirectory();

    File createDirectory(String name);

    File createFile(String name, String content);

    File rootDirectory();

    default void executeExtension(Extension extension) {
        extension.execute(this);
    }

}
