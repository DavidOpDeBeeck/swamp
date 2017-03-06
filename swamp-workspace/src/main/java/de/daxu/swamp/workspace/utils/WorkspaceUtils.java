package de.daxu.swamp.workspace.utils;

import java.io.File;
import java.util.UUID;

public class WorkspaceUtils {

    public static String randomName() {
        return UUID.randomUUID().toString();
    }

    public static String getAbsolutePath(File directory, String name) {
        return String.format("%s%s%s", directory.getAbsolutePath(), File.separator, name);
    }
}
