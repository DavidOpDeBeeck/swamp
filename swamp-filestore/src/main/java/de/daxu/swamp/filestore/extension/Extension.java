package de.daxu.swamp.filestore.extension;

import de.daxu.swamp.filestore.FileStore;

import java.io.File;

public interface Extension {

    File execute(FileStore fileStore);

}
