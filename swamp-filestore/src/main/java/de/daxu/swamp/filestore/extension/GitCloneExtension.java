package de.daxu.swamp.filestore.extension;

import de.daxu.swamp.filestore.FileStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static java.lang.String.format;
import static org.eclipse.jgit.api.Git.cloneRepository;

public class GitCloneExtension implements Extension {

    public static GitCloneExtension clone(String uri, String branch, String directoryName) {
        return new GitCloneExtension(uri, branch, directoryName);
    }

    private final Logger logger = LoggerFactory.getLogger(GitCloneExtension.class);

    private String uri;
    private String branch;
    private String directoryName;

    private GitCloneExtension(String uri, String branch, String directoryName) {
        this.uri = uri;
        this.branch = branch;
        this.directoryName = directoryName;
    }

    @Override
    public File execute(FileStore fileStore) {
        File cloneDirectory = fileStore.createDirectory();

        try {
            logger.info("Started cloning repository: {} into directory: {}", uri, branch, cloneDirectory.getAbsolutePath());

            cloneRepository()
                    .setURI(uri)
                    .setDirectory(cloneDirectory)
                    .setBranch(branch)
                    .call();

            logger.info("Succeeded cloning repository: {}", uri);
            return fileStore.getDirectory(format("%s%s%s", cloneDirectory.getName(), File.separator, directoryName));
        } catch(Exception e) {
            logger.info("Failed cloning repository: {}, exception: {}", uri, e.getMessage());
            throw new ExtensionException(e);
        }
    }
}
