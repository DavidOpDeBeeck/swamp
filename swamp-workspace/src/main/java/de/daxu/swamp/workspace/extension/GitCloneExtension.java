package de.daxu.swamp.workspace.extension;

import de.daxu.swamp.workspace.Workspace;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.eclipse.jgit.api.Git.cloneRepository;

public class GitCloneExtension implements Extension {

    public static GitCloneExtension clone(String uri, String branch) {
        return new GitCloneExtension(uri, branch);
    }

    private final Logger logger = LoggerFactory.getLogger(GitCloneExtension.class);

    private String uri;
    private String branch;

    private GitCloneExtension(String uri, String branch) {
        this.uri = uri;
        this.branch = branch;
    }

    @Override
    public void execute(Workspace workspace) {
        try {
            File cloneDirectory = workspace.rootDirectory();
            cloneRepositoryInto(cloneDirectory);
        } catch(Exception e) {
            logger.warn("Failed cloning repository: {}, exception: {}", uri, e.getMessage());
            throw new ExtensionException(e);
        }
    }

    private void cloneRepositoryInto(File cloneDirectory) throws GitAPIException {
        logger.debug("Started cloning repository: {} into directory: {}", uri, cloneDirectory.getAbsolutePath());

        cloneRepository()
                .setURI(uri)
                .setDirectory(cloneDirectory)
                .setBranch(branch)
                .call();

        logger.debug("Succeeded cloning repository: {}", uri);
    }
}
