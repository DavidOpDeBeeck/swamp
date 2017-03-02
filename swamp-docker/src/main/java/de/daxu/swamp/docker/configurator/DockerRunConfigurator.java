package de.daxu.swamp.docker.configurator;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import de.daxu.swamp.core.configuration.DockerfileConfiguration;
import de.daxu.swamp.core.configuration.GitConfiguration;
import de.daxu.swamp.core.configuration.ImageConfiguration;
import de.daxu.swamp.core.configuration.RunConfigurator;
import de.daxu.swamp.deploy.callback.ProgressCallback;
import de.daxu.swamp.docker.command.CommandCallback;
import de.daxu.swamp.workspace.Workspace;
import de.daxu.swamp.workspace.extension.GitCloneExtension;
import de.daxu.swamp.workspace.manager.WorkspaceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class DockerRunConfigurator implements RunConfigurator<CreateContainerCmd> {

    private final Logger logger = LoggerFactory.getLogger(DockerRunConfigurator.class);

    private final DockerClient client;
    private final WorkspaceManager workspaceManager;
    private final ProgressCallback<String> progressCallback;

    public DockerRunConfigurator(DockerClient client, WorkspaceManager workspaceManager, ProgressCallback<String> progressCallback) {
        this.client = client;
        this.workspaceManager = workspaceManager;
        this.progressCallback = progressCallback;
    }

    @Override
    public CreateContainerCmd configure(GitConfiguration config) {
        Workspace workspace = workspaceManager.createWorkspace();
        String tag = UUID.randomUUID().toString();

        workspace.executeExtension(GitCloneExtension.clone(config.getUrl(), config.getBranch()));
        File directory = workspace.getPath(config.getPath());

        doWithCountDownLatch(countDownLatch ->
                client.buildImageCmd(directory)
                        .withTag(tag)
                        .exec(onImageBuildCallback(countDownLatch::countDown)));

        workspace.clear();
        return client.createContainerCmd(tag);
    }

    @Override
    public CreateContainerCmd configure(ImageConfiguration config) {
        return client.createContainerCmd(config.getName());
    }

    @Override
    public CreateContainerCmd configure(DockerfileConfiguration config) {
        Workspace workspace = workspaceManager.createWorkspace();
        File file = workspace.createFile("Dockerfile", config.getDockerfile());
        String tag = UUID.randomUUID().toString();

        doWithCountDownLatch(countDownLatch ->
                client.buildImageCmd(file)
                        .withTag(tag)
                        .withRemove(true)
                        .exec(onImageBuildCallback(countDownLatch::countDown)));

        workspace.clear();
        return client.createContainerCmd(tag);
    }

    private void doWithCountDownLatch(Consumer<CountDownLatch> consumer) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            consumer.accept(countDownLatch);
            countDownLatch.await();
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private CommandCallback<BuildImageResultCallback, BuildResponseItem> onImageBuildCallback(Runnable runnable) {
        return new CommandCallback.Builder<BuildImageResultCallback, BuildResponseItem>()
                .withOnNextCallback((x) -> progressCallback.onProgress(x.getStream()))
                .withOnCompletedCallback(runnable::run)
                .build();
    }
}
