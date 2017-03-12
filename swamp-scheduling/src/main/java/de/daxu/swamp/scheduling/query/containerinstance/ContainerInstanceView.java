package de.daxu.swamp.scheduling.query.containerinstance;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.daxu.swamp.common.cqrs.EntityView;
import de.daxu.swamp.common.jackson.LocalDateTimeSerializer;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "container_instance_view")
@SuppressWarnings("unused")
public class ContainerInstanceView extends EntityView {

    @Embedded
    @NotNull(message = "{NotNull.ContainerInstanceView.containerInstanceId}")
    private ContainerInstanceId containerInstanceId;

    @Column(name = "container_id")
    private ContainerId containerId;

    @Column(name = "initialized_at")
    private LocalDateTime initializedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "stopped_at")
    private LocalDateTime stoppedAt;

    @Column(name = "removed_at")
    private LocalDateTime removedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ContainerInstanceStatus status;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "server_view_id")
    private ServerView server;

    //@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    //@JoinColumn( name = "run_configuration_view_id" )
    @Embedded
    private RunConfigurationView runConfiguration;

    @Enumerated(EnumType.STRING)
    @Column(name = "stop_reason")
    private ContainerInstanceStopReason stopReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "remove_reason")
    private ContainerInstanceRemoveReason removeReason;

    @ElementCollection
    @CollectionTable(name = "container_instance_warning_view", joinColumns = @JoinColumn(name = "container_instance_view_id"))
    private Set<String> warnings;

    private ContainerInstanceView() {
    }

    private ContainerInstanceView(ContainerInstanceId containerInstanceId,
                                  ContainerId containerId,
                                  LocalDateTime initializedAt,
                                  LocalDateTime createdAt,
                                  LocalDateTime startedAt,
                                  LocalDateTime stoppedAt,
                                  LocalDateTime removedAt,
                                  ContainerInstanceStatus status,
                                  ServerView server,
                                  RunConfigurationView runConfiguration,
                                  ContainerInstanceStopReason stopReason,
                                  ContainerInstanceRemoveReason removeReason,
                                  Set<String> warnings) {
        this.containerInstanceId = containerInstanceId;
        this.containerId = containerId;
        this.initializedAt = initializedAt;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.stoppedAt = stoppedAt;
        this.removedAt = removedAt;
        this.status = status;
        this.server = server;
        this.stopReason = stopReason;
        this.removeReason = removeReason;
        this.runConfiguration = runConfiguration;
        this.warnings = warnings;
    }

    public void setContainerInstanceId(ContainerInstanceId containerInstanceId) {
        this.containerInstanceId = containerInstanceId;
    }

    void setContainerId(ContainerId containerId) {
        this.containerId = containerId;
    }

    void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    void setStoppedAt(LocalDateTime stoppedAt) {
        this.stoppedAt = stoppedAt;
    }

    void setRemovedAt(LocalDateTime removedAt) {
        this.removedAt = removedAt;
    }

    void addWarnings(Set<String> warnings) {
        if (this.warnings == null)
            this.warnings = new HashSet<>();
        this.warnings.addAll(warnings);
    }

    void setStatus(ContainerInstanceStatus status) {
        this.status = status;
    }

    public void setStopReason(ContainerInstanceStopReason stopReason) {
        this.stopReason = stopReason;
    }

    public void setRemoveReason(ContainerInstanceRemoveReason removeReason) {
        this.removeReason = removeReason;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }

    public ContainerId getContainerId() {
        return containerId;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getInitializedAt() {
        return initializedAt;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getStoppedAt() {
        return stoppedAt;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getRemovedAt() {
        return removedAt;
    }

    public ContainerInstanceStatus getStatus() {
        return status;
    }

    public ServerView getServer() {
        return server;
    }

    public RunConfigurationView getRunConfiguration() {
        return runConfiguration;
    }

    public ContainerInstanceStopReason getStopReason() {
        return stopReason;
    }

    public ContainerInstanceRemoveReason getRemoveReason() {
        return removeReason;
    }

    public Set<String> getWarnings() {
        return warnings;
    }

    public static class Builder {

        private ContainerInstanceId containerInstanceId;
        private ContainerId containerId;
        private LocalDateTime initializedAt;
        private LocalDateTime createdAt;
        private LocalDateTime startedAt;
        private LocalDateTime stoppedAt;
        private LocalDateTime removedAt;
        private ContainerInstanceStatus status;
        private ServerView server;
        private RunConfigurationView runConfiguration;
        private ContainerInstanceStopReason stopReason;
        private ContainerInstanceRemoveReason removeReason;
        private Set<String> warnings;

        static Builder aContainerInstanceView() {
            return new Builder();
        }

        public Builder withContainerInstanceId(ContainerInstanceId containerInstanceId) {
            this.containerInstanceId = containerInstanceId;
            return this;
        }

        public Builder withContainerId(ContainerId containerId) {
            this.containerId = containerId;
            return this;
        }

        public Builder withInitializedAt(LocalDateTime dateInitialized) {
            this.initializedAt = dateInitialized;
            return this;
        }

        public Builder withCreatedAt(LocalDateTime dateCreated) {
            this.createdAt = dateCreated;
            return this;
        }

        public Builder withStartedAt(LocalDateTime dateStarted) {
            this.startedAt = dateStarted;
            return this;
        }

        public Builder withStoppedAt(LocalDateTime dateStopped) {
            this.stoppedAt = dateStopped;
            return this;
        }

        public Builder withRemovedAt(LocalDateTime dateRemoved) {
            this.removedAt = dateRemoved;
            return this;
        }

        public Builder withStatus(ContainerInstanceStatus status) {
            this.status = status;
            return this;
        }

        public Builder withServer(ServerView server) {
            this.server = server;
            return this;
        }

        public Builder withStopReason(ContainerInstanceStopReason stopReason) {
            this.stopReason = stopReason;
            return this;
        }

        public Builder withRemoveReason(ContainerInstanceRemoveReason removeReason) {
            this.removeReason = removeReason;
            return this;
        }

        public Builder withWarnings(Set<String> warnings) {
            this.warnings = warnings;
            return this;
        }

        public ContainerInstanceView build() {
            return new ContainerInstanceView(
                    containerInstanceId,
                    containerId,
                    initializedAt,
                    createdAt,
                    startedAt,
                    stoppedAt,
                    removedAt,
                    status,
                    server,
                    runConfiguration,
                    stopReason,
                    removeReason,
                    warnings);
        }
    }
}
