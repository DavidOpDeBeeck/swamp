package de.daxu.swamp.scheduler.core;

import de.daxu.swamp.common.Identifiable;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;

import java.util.Date;
import java.util.UUID;

public class ContainerInstance extends Identifiable {

    private ProjectInstance projectInstance;

    private Container container;
    private Server server;

    private String internalContainerId;
    private Status status;

    private Date startedAt;
    private Date finishedAt;

    private StringBuilder logs;

    private ContainerInstance( ProjectInstance projectInstance, Container container, Server server, String internalContainerId, Status status, Date startedAt ) {
        this.projectInstance = projectInstance;
        this.id = UUID.randomUUID().toString();
        this.container = container;
        this.server = server;
        this.internalContainerId = internalContainerId;
        this.status = status;
        this.startedAt = startedAt;
        this.logs = new StringBuilder();
    }

    public ProjectInstance getProjectInstance() {
        return projectInstance;
    }

    public void addLog( String logs ) {
        this.logs.append( logs );
    }

    public void setStatus( Status status ) {
        this.status = status;
    }

    public void setFinishedAt( Date finishedAt ) {
        this.finishedAt = finishedAt;
    }

    public Container getContainer() {
        return container;
    }

    public Server getServer() {
        return server;
    }

    public String getInternalContainerId() {
        return internalContainerId;
    }

    public Status getStatus() {
        return status;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public String getLogs() {
        return logs.toString();
    }

    public static class ContainerInstanceBuilder {

        private ProjectInstance projectInstance;
        private Container container;
        private Server server;
        private String internalContainerId;
        private Date startDate;

        public static ContainerInstanceBuilder aContainerInstance() {
            return new ContainerInstanceBuilder();
        }

        public ContainerInstanceBuilder withProjectInstance( ProjectInstance projectInstance ) {
            this.projectInstance = projectInstance;
            return this;
        }

        public ContainerInstanceBuilder withInternalContainerId( String internalContainerId ) {
            this.internalContainerId = internalContainerId;
            return this;
        }

        public ContainerInstanceBuilder withContainer( Container container ) {
            this.container = container;
            return this;
        }

        public ContainerInstanceBuilder withServer( Server server ) {
            this.server = server;
            return this;
        }

        public ContainerInstanceBuilder withStartDate( Date startDate ) {
            this.startDate = startDate;
            return this;
        }

        public ContainerInstance build() {
            return new ContainerInstance( projectInstance, container, server, internalContainerId, Status.STARTED, startDate );
        }
    }

    public enum Status {
        STARTED,
        RUNNING,
        PAUSED,
        RESTARTING,
        EXITED
    }
}
