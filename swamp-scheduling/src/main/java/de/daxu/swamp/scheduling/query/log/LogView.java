package de.daxu.swamp.scheduling.query.log;

import de.daxu.swamp.common.cqrs.EntityView;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang.StringUtils.EMPTY;

@Entity
@Table(name = "container_instance_log_view")
@SuppressWarnings("unused")
public class LogView extends EntityView {

    @Embedded
    @NotNull(message = "{NotNull.ContainerInstanceView.containerInstanceId}")
    private ContainerInstanceId containerInstanceId;

    @Lob
    @Column(name = "running_log")
    private String runningLog;

    @Lob
    @Column(name = "creation_log")
    private String creationLog;

    private LogView() {
    }

    private LogView(ContainerInstanceId containerInstanceId, String creationLog, String runningLog) {
        this.containerInstanceId = containerInstanceId;
        this.creationLog = creationLog;
        this.runningLog = runningLog;
    }

    public void setContainerInstanceId(ContainerInstanceId containerInstanceId) {
        this.containerInstanceId = containerInstanceId;
    }

    void addRunningLog(String log) {
        if (this.runningLog == null) {
            this.runningLog = EMPTY;
        }
        this.runningLog += log;
    }

    void addCreationLog(String log) {
        if (this.creationLog == null) {
            this.creationLog = EMPTY;
        }
        this.creationLog += log;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }

    public String getCreationLog() {
        return creationLog;
    }

    public String getRunningLog() {
        return runningLog;
    }

    public static class Builder {

        private ContainerInstanceId containerInstanceId;
        private String runningLog;
        private String creationLog;

        static Builder aLogView() {
            return new Builder();
        }

        public Builder withContainerInstanceId(ContainerInstanceId containerInstanceId) {
            this.containerInstanceId = containerInstanceId;
            return this;
        }

        public Builder withRunningLog(String runningLog) {
            this.runningLog = runningLog;
            return this;
        }

        public Builder withCreationLog(String creationLog) {
            this.creationLog = creationLog;
            return this;
        }

        public LogView build() {
            return new LogView(containerInstanceId, creationLog, runningLog);
        }
    }
}
