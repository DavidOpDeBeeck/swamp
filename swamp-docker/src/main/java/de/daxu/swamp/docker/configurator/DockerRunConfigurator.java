package de.daxu.swamp.docker.configurator;

import de.daxu.swamp.core.configuration.DockerfileConfiguration;
import de.daxu.swamp.core.configuration.GitConfiguration;
import de.daxu.swamp.core.configuration.ImageConfiguration;
import de.daxu.swamp.core.configuration.RunConfigurator;
import de.daxu.swamp.deploy.notifier.Notifier;
import de.daxu.swamp.docker.client.DockerClient;
import de.daxu.swamp.workspace.Workspace;
import de.daxu.swamp.workspace.extension.GitCloneExtension;
import de.daxu.swamp.workspace.manager.WorkspaceManager;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class DockerRunConfigurator implements RunConfigurator<String> {

    private final DockerClient client;
    private final WorkspaceManager workspaceManager;
    private final Notifier<String> notifier;

    DockerRunConfigurator(DockerClient client,
                          WorkspaceManager workspaceManager,
                          Notifier<String> notifier) {
        this.client = client;
        this.workspaceManager = workspaceManager;
        this.notifier = notifier;
    }

    @Override
    public String configure(GitConfiguration config) {
        Workspace workspace = workspaceManager.createWorkspace();
        String tag = UUID.randomUUID().toString();

        workspace.executeExtension(GitCloneExtension.clone(config.getUrl(), config.getBranch()));
        File directory = workspace.getPath(config.getPath());

        doWithCountDownLatch(countDownLatch ->
                client.buildContainer(directory, tag, buildNotifier(countDownLatch)));

        workspace.clear();
        return tag;
    }

    @Override
    public String configure(ImageConfiguration config) {
        return config.getName();
    }

    @Override
    public String configure(DockerfileConfiguration config) {
        String tag = UUID.randomUUID().toString();
        Workspace workspace = workspaceManager.createWorkspace();
        File file = workspace.createFile("Dockerfile", config.getDockerfile());

        doWithCountDownLatch(countDownLatch ->
                client.buildContainer(file, tag, buildNotifier(countDownLatch)));

        workspace.clear();
        return tag;
    }

    private Notifier<String> buildNotifier(CountDownLatch countDownLatch) {
        return new Notifier.Builder<String>()
                .withErrorNotifier(notifier::onError)
                .withNextNotifier(notifier::onProgress)
                .withCompletionNotifier(notifier::onCompletion)
                .withCompletionNotifier(countDownLatch::countDown)
                .build();
    }

    private void doWithCountDownLatch(Consumer<CountDownLatch> consumer) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            consumer.accept(countDownLatch);
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
