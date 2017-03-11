package de.daxu.swamp.docker.configurator;

import de.daxu.swamp.core.configuration.DockerfileConfiguration;
import de.daxu.swamp.core.configuration.GitConfiguration;
import de.daxu.swamp.core.configuration.ImageConfiguration;
import de.daxu.swamp.core.configuration.RunConfigurator;
import de.daxu.swamp.deploy.notifier.DeployNotifier;
import de.daxu.swamp.docker.behaviour.DockerBehaviour;
import de.daxu.swamp.workspace.extension.GitCloneExtension;
import de.daxu.swamp.workspace.manager.WorkspaceManager;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class DockerRunConfigurator implements RunConfigurator<String> {

    private static final String DOCKERFILE = "Dockerfile";

    private final DockerBehaviour client;
    private final WorkspaceManager workspaceManager;
    private final DeployNotifier<String> notifier;

    DockerRunConfigurator(DockerBehaviour client,
                          WorkspaceManager workspaceManager,
                          DeployNotifier<String> notifier) {
        this.client = client;
        this.workspaceManager = workspaceManager;
        this.notifier = notifier;
    }

    @Override
    public String configure(GitConfiguration config) {
        String tag = randomTagId();

        workspaceManager.doInWorkspace(workspace -> {
            workspace.executeExtension(GitCloneExtension.clone(config.getUrl(), config.getBranch()));
            File directory = workspace.getPath(config.getPath());

            doWithCountDownLatch(countDownLatch ->
                    client.buildContainer(directory, tag, buildNotifier(countDownLatch)));
        });

        return tag;
    }

    @Override
    public String configure(ImageConfiguration config) {
        return config.getName();
    }

    @Override
    public String configure(DockerfileConfiguration config) {
        String tag = randomTagId();

        workspaceManager.doInWorkspace(workspace -> {
            File file = workspace
                    .createFile(DOCKERFILE, config.getDockerfile());

            doWithCountDownLatch(countDownLatch ->
                    client.buildContainer(file, tag, buildNotifier(countDownLatch)));
        });

        return tag;
    }

    private String randomTagId() {
        return UUID.randomUUID().toString();
    }

    private DeployNotifier<String> buildNotifier(CountDownLatch countDownLatch) {
        return new DeployNotifier.Builder<String>()
                .withErrorNotifier(notifier::triggerError)
                .withProgressNotifier(notifier::triggerProgress)
                .withCompletionNotifier(notifier::triggerCompletion)
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
