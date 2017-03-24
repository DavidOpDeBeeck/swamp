package de.daxu.swamp.scheduling;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import de.daxu.swamp.common.time.Dates;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.project.ProjectId;
import de.daxu.swamp.scheduling.query.build.BuildView;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;
import de.daxu.swamp.scheduling.query.containerinstance.ServerView;
import de.daxu.swamp.scheduling.query.log.LogView;
import de.daxu.swamp.scheduling.query.project.ProjectView;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.core.LocationTestConstants.Servers.*;
import static de.daxu.swamp.core.ProjectTestConstants.Projects.*;
import static de.daxu.swamp.deploy.ContainerTestConstants.ANOTHER_CONTAINER_ID;
import static de.daxu.swamp.deploy.ContainerTestConstants.A_CONTAINER_ID;
import static de.daxu.swamp.scheduling.ViewTestConstants.ContainerInstanceViews.ANOTHER_CONTAINER_INSTANCE_ID;
import static de.daxu.swamp.scheduling.ViewTestConstants.ContainerInstanceViews.A_CONTAINER_INSTANCE_ID;
import static de.daxu.swamp.scheduling.ViewTestConstants.ServerViews.aServerView;
import static de.daxu.swamp.scheduling.ViewTestConstants.ServerViews.anotherServerView;

public class ViewTestConstants {

    public static class ProjectViews {

        public static final ProjectId A_PROJECT_ID = ProjectId.random();
        public static final ProjectId ANOTHER_PROJECT_ID = ProjectId.random();

        public static ProjectView aProjectView() {
            return aProjectViewBuilder()
                    .build();
        }

        public static ProjectView aProjectView(BuildView... buildViews) {
            return aProjectViewBuilder()
                    .withBuilds(newHashSet(buildViews))
                    .build();
        }

        public static ProjectView anotherProjectView() {
            return aProjectViewBuilder()
                    .withProjectId(ANOTHER_PROJECT_ID)
                    .withName(ANOTHER_PROJECT_NAME)
                    .withDescription(ANOTHER_PROJECT_DESCRIPTION)
                    .withBuilds(Sets.newHashSet())
                    .build();
        }

        public static ProjectView.Builder aProjectViewBuilder() {
            return new ProjectView.Builder()
                    .withProjectId(A_PROJECT_ID)
                    .withName(A_PROJECT_NAME)
                    .withDescription(A_PROJECT_DESCRIPTION)
                    .withBuilds(Sets.newHashSet());
        }
    }

    public static class BuildViews {

        public static final BuildId A_BUILD_ID = BuildId.random();
        public static final BuildId ANOTHER_BUILD_ID = BuildId.random();
        public static final int A_BUILD_SEQUENCE = 0;
        public static final int ANOTHER_BUILD_SEQUENCE = 1;
        public static final LocalDateTime A_BUILD_INIT_DATE = Dates.now();
        public static final LocalDateTime ANOTHER_BUILD_INIT_DATE = Dates.now();
        public static final ImmutableMap<ContainerInstanceId, ContainerInstanceStatus> A_BUILD_CONTAINER_MAP
                = new ImmutableMap.Builder<ContainerInstanceId, ContainerInstanceStatus>()
                .put(A_CONTAINER_INSTANCE_ID, ContainerInstanceStatus.CREATED)
                .build();
        public static final ImmutableMap<ContainerInstanceId, ContainerInstanceStatus> ANOTHER_BUILD_CONTAINER_MAP
                = new ImmutableMap.Builder<ContainerInstanceId, ContainerInstanceStatus>()
                .put(ANOTHER_CONTAINER_INSTANCE_ID, ContainerInstanceStatus.CREATED)
                .build();

        public static BuildView aBuildView() {
            return aBuildViewBuilder()
                    .build();
        }

        public static BuildView aBuildView(ContainerInstanceView... containers) {
            return aBuildViewBuilder()
                    .withContainers(Stream.of(containers)
                            .collect(Collectors.toMap(ContainerInstanceView::getContainerInstanceId, ContainerInstanceView::getStatus)))
                    .build();
        }

        public static BuildView anotherBuildView() {
            return aBuildViewBuilder()
                    .withBuildId(ANOTHER_BUILD_ID)
                    .withSequence(ANOTHER_BUILD_SEQUENCE)
                    .withInitializedAt(ANOTHER_BUILD_INIT_DATE)
                    .withContainers(ANOTHER_BUILD_CONTAINER_MAP)
                    .build();
        }

        public static BuildView.Builder aBuildViewBuilder() {
            return new BuildView.Builder()
                    .withBuildId(A_BUILD_ID)
                    .withSequence(A_BUILD_SEQUENCE)
                    .withInitializedAt(A_BUILD_INIT_DATE)
                    .withContainers(A_BUILD_CONTAINER_MAP);
        }
    }

    public static class ContainerInstanceViews {

        public static final ContainerInstanceId A_CONTAINER_INSTANCE_ID = ContainerInstanceId.random();
        public static final ContainerInstanceId ANOTHER_CONTAINER_INSTANCE_ID = ContainerInstanceId.random();
        public static final LocalDateTime A_CONTAINER_INSTANCE_CREATED_DATE = Dates.now();
        public static final LocalDateTime ANOTHER_CONTAINER_INSTANCE_CREATED_DATE = Dates.now();

        public static ContainerInstanceView aContainerInstanceView() {
            return new ContainerInstanceView.Builder()
                    .withContainerId(A_CONTAINER_ID)
                    .withContainerInstanceId(A_CONTAINER_INSTANCE_ID)
                    .withCreatedAt(A_CONTAINER_INSTANCE_CREATED_DATE)
                    .withServer(aServerView())
                    .withStatus(ContainerInstanceStatus.CREATED)
                    .build();
        }

        public static ContainerInstanceView anotherContainerInstanceView() {
            return new ContainerInstanceView.Builder()
                    .withContainerId(ANOTHER_CONTAINER_ID)
                    .withContainerInstanceId(ANOTHER_CONTAINER_INSTANCE_ID)
                    .withCreatedAt(ANOTHER_CONTAINER_INSTANCE_CREATED_DATE)
                    .withServer(anotherServerView())
                    .withStatus(ContainerInstanceStatus.CREATED)
                    .build();
        }
    }

    public static class ServerViews {

        public static ServerView aServerView() {
            return new ServerView.Builder()
                    .withIp(A_SERVER_IP)
                    .withName(A_SERVER_NAME)
                    .build();
        }

        public static ServerView anotherServerView() {
            return new ServerView.Builder()
                    .withIp(ANOTHER_SERVER_IP)
                    .withName(ANOTHER_SERVER_NAME)
                    .build();
        }
    }

    public static class LogViews {

        public static final String A_CREATION_LOG = "this is a creation log";
        public static final String ANOTHER_CREATION_LOG = "this is another creation log";
        public static final String A_RUNNING_LOG = "this is a running log";
        public static final String ANOTHER_RUNNING_LOG = "this is another running log";

        public static LogView aLogView() {
            return new LogView.Builder()
                    .withContainerInstanceId(A_CONTAINER_INSTANCE_ID)
                    .withRunningLog(A_RUNNING_LOG)
                    .withCreationLog(A_CREATION_LOG)
                    .build();
        }

        public static LogView anotherLogView() {
            return new LogView.Builder()
                    .withContainerInstanceId(ANOTHER_CONTAINER_INSTANCE_ID)
                    .withRunningLog(ANOTHER_RUNNING_LOG)
                    .withCreationLog(ANOTHER_CREATION_LOG)
                    .build();
        }
    }
}
