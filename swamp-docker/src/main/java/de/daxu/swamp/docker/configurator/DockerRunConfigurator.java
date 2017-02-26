package de.daxu.swamp.docker.configurator;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import de.daxu.swamp.core.configuration.DockerfileConfiguration;
import de.daxu.swamp.core.configuration.GitConfiguration;
import de.daxu.swamp.core.configuration.ImageConfiguration;
import de.daxu.swamp.core.configuration.RunConfigurator;
import de.daxu.swamp.filestore.FileStore;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static de.daxu.swamp.docker.command.BuildImageCommandCallback.onImageBuild;

public class DockerRunConfigurator implements RunConfigurator<CreateContainerCmd> {

    private final Logger logger = LoggerFactory.getLogger(DockerRunConfigurator.class);

    private final DockerClient client;
    private final FileStore fileStore;

    public DockerRunConfigurator(DockerClient client, FileStore fileStore) {
        this.client = client;
        this.fileStore = fileStore;
    }

    @Override
    public CreateContainerCmd configure(GitConfiguration configuration) {
        throw new NotImplementedException();
    }

    @Override
    public CreateContainerCmd configure(ImageConfiguration configuration) {
        return client.createContainerCmd(configuration.getName());
    }

    @Override
    public CreateContainerCmd configure(DockerfileConfiguration configuration) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String tag = UUID.randomUUID().toString();

        try {
            File directory = fileStore.createTempDirectory();
            File file = fileStore.createTempFile(directory, configuration.getDockerfile());

            client.buildImageCmd(file)
                    .withTag(tag)
                    .exec(onImageBuild(countDownLatch::countDown));

            countDownLatch.await();
        } catch(InterruptedException e) {
            logger.error("Error creating Dockerfile: ", e.getMessage());
        }

        return client.createContainerCmd(tag);
    }
}
