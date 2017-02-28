package de.daxu.swamp.workspace;

import java.io.File;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

class WorkspaceUtils {

    static String randomName() {
        return UUID.randomUUID().toString();
    }

    static String getAbsolutePath(File directory, String name) {
        return format("%s%s%s", directory.getAbsolutePath(), File.separator, name);
    }

    static void isValidFileName(String name) {
        if(isEmpty(name)) {
            throw new WorkspaceException("Filename should not be empty");
        }
    }

    static void isValidDirectoryName(String name) {
        if(isEmpty(name)) {
            throw new WorkspaceException("Directory name should not be empty");
        }
    }
}
