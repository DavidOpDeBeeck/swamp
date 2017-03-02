package de.daxu.swamp.workspace.utils;

import de.daxu.swamp.workspace.WorkspaceException;

import java.io.File;
import java.util.UUID;

import static org.springframework.util.StringUtils.isEmpty;

public class WorkspaceUtils {

    public static String randomName() {
        return UUID.randomUUID().toString();
    }

    public static String getAbsolutePath(File directory, String name) {
        return String.format("%s%s%s", directory.getAbsolutePath(), File.separator, name);
    }

    public static void isValidFileName(String name) {
        if(isEmpty(name)) {
            throw new WorkspaceException("Filename should not be empty");
        }
    }

    public static void isValidDirectoryName(String name) {
        if(isEmpty(name)) {
            throw new WorkspaceException("Directory name should not be empty");
        }
    }
}
