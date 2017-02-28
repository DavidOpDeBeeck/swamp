package de.daxu.swamp.workspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

public class SimpleRemovableWorkspace
        extends SimpleWorkspace
        implements RemovableWorkspace {

    private final Logger logger = LoggerFactory.getLogger(SimpleRemovableWorkspace.class);

    SimpleRemovableWorkspace(String path) {
        super(path);
    }

    @Override
    public void clear() {
        File[] files = rootDirectory().listFiles();
        if(files != null) {
            logger.debug("Removing all files in workspace: {}", rootDirectory().getAbsolutePath());
            Arrays.stream(files).forEach(File::delete);
        }
    }
}
