package de.daxu.swamp.scheduling.query.build;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.daxu.swamp.common.cqrs.EntityView;
import de.daxu.swamp.common.jackson.LocalDateTimeSerializer;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.build.BuildStatus;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.INITIALIZED;
import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.REMOVED;

@Entity
@Table(name = "build_view")
@SuppressWarnings("unused")
public class BuildView extends EntityView {

    @Embedded
    @NotNull(message = "{NotNull.BuildView.buildId}")
    private BuildId buildId;

    @Column(name = "sequence")
    @Min(value = 0, message = "{Min.BuildView.sequence}")
    @NotNull(message = "{NotNull.BuildView.sequence}")
    private Integer sequence;

    @Column(name = "initialized_at")
    private LocalDateTime initializedAt;

    @ElementCollection
    @MapKeyColumn(name = "container_instance_id")
    @Column(name = "status")
    @CollectionTable(name = "build_container_view", joinColumns = @JoinColumn(name = "build_view_id"))
    private Map<ContainerInstanceId, ContainerInstanceStatus> containers;

    private BuildView() {
    }

    private BuildView(BuildId buildId,
                     int sequence,
                     LocalDateTime initializedAt,
                      Map<ContainerInstanceId, ContainerInstanceStatus> containers) {
        this.buildId = buildId;
        this.sequence = sequence;
        this.initializedAt = initializedAt;
        this.containers = containers;
    }

    void addContainerInstance(ContainerInstanceId containerInstanceId) {
        containers.put(containerInstanceId, INITIALIZED);
    }

    void setContainerInstanceStatus(ContainerInstanceId containerInstanceId, ContainerInstanceStatus status) {
        containers.put(containerInstanceId, status);
    }

    public BuildId getBuildId() {
        return buildId;
    }

    public Integer getSequence() {
        return sequence;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getInitializedAt() {
        return initializedAt;
    }

    public BuildStatus getStatus() {
        boolean hasLiveContainers = containers.values()
                .stream()
                .anyMatch(s -> s != REMOVED);

        return hasLiveContainers ? BuildStatus.INPROGRESS : BuildStatus.FINISHED;
    }

    public Set<ContainerInstanceId> getContainers() {
        return containers.keySet();
    }

    public static class Builder {

        private BuildId buildId;
        private int sequence;
        private LocalDateTime initializedAt;
        private Map<ContainerInstanceId, ContainerInstanceStatus> containers = newHashMap();

        public static Builder aBuildView() {
            return new Builder();
        }

        public Builder withBuildId(BuildId buildId) {
            this.buildId = buildId;
            return this;
        }

        public Builder withSequence(int sequence) {
            this.sequence = sequence;
            return this;
        }

        public Builder withInitializedAt(LocalDateTime dateInitialized) {
            this.initializedAt = dateInitialized;
            return this;
        }

        public Builder withContainers(Map<ContainerInstanceId, ContainerInstanceStatus> containers) {
            this.containers = containers;
            return this;
        }

        public BuildView build() {
            return new BuildView(buildId, sequence, initializedAt, containers);
        }
    }
}
