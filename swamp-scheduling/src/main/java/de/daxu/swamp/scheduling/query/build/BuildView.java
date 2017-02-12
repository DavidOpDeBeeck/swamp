package de.daxu.swamp.scheduling.query.build;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.daxu.swamp.common.cqrs.EntityView;
import de.daxu.swamp.common.jackson.LocalDateTimeSerializer;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{NotNull.BuildView.status}")
    private BuildStatus status;

    @ElementCollection
    @CollectionTable(name = "build_container_view", joinColumns = @JoinColumn(name = "build_view_id"))
    private Set<ContainerInstanceId> containers;

    private BuildView() {
    }

    public BuildView(BuildId buildId,
                     int sequence,
                     LocalDateTime initializedAt,
                     BuildStatus status,
                     Set<ContainerInstanceId> containers) {
        this.buildId = buildId;
        this.sequence = sequence;
        this.initializedAt = initializedAt;
        this.status = status;
        this.containers = containers;
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
        return status;
    }

    public Set<ContainerInstanceId> getContainers() {
        return containers;
    }

    public static class Builder {

        private BuildId buildId;
        private int sequence;
        private LocalDateTime initializedAt;
        private BuildStatus status;
        private Set<ContainerInstanceId> containers;

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

        public Builder withStatus(BuildStatus status) {
            this.status = status;
            return this;
        }

        public Builder withContainers(Set<ContainerInstanceId> containers) {
            this.containers = containers;
            return this;
        }

        public BuildView build() {
            return new BuildView(buildId, sequence, initializedAt, status, containers);
        }
    }
}
