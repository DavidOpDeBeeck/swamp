package de.daxu.swamp.workspace.utils;

import de.daxu.swamp.workspace.WorkspaceException;

import static org.springframework.util.StringUtils.isEmpty;

public class WorkspaceValidator {

    public static void isValidFileName(String name) {
        if (isEmpty(name))
            throw new WorkspaceException("Filename should not be empty");
    }

    public static void isValidDirectoryName(String name) {
        if (isEmpty(name))
            throw new WorkspaceException("Directory name should not be empty");
    }
}
