package de.daxu.swamp.scheduling.read.containerinstance;

import de.daxu.swamp.common.jpa.Identifiable;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.write.projectinstance.ProjectInstanceId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table( name = "container_instance_view" )
@SuppressWarnings( "unused" )
public class ContainerInstanceView extends Identifiable {

    @Embedded
    @NotNull( message = "{NotNull.ContainerInstanceView.containerInstanceId}" )
    private ContainerInstanceId containerInstanceId;

    @Column( name = "internal_container_name" )
    private String internalContainerName;

    @Column( name = "internal_container_id" )
    private String internalContainerId;

    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "date_scheduled" )
    private Date dateScheduled;

    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "date_created" )
    private Date dateCreated;

    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "date_started" )
    private Date dateStarted;

    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "date_stopped" )
    private Date dateStopped;

    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "date_removed" )
    private Date dateRemoved;

    @Lob
    @Column( name = "log" )
    private String log = "";

    @Enumerated( EnumType.STRING )
    @Column( name = "status" )
    private ContainerInstanceStatus status;

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinColumn( name = "server_view_id" )
    private ServerView server;

    //@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    //@JoinColumn( name = "run_configuration_view_id" )
    @Embedded
    private RunConfigurationView runConfiguration;

    private ContainerInstanceView() {
    }

    private ContainerInstanceView( ProjectInstanceId projectInstanceId,
                                   ContainerInstanceId containerInstanceId,
                                   String internalContainerName,
                                   String internalContainerId,
                                   Date dateScheduled,
                                   Date dateCreated,
                                   Date dateStarted,
                                   Date dateStopped,
                                   Date dateRemoved,
                                   String log,
                                   ContainerInstanceStatus status,
                                   ServerView server,
                                   RunConfigurationView runConfiguration ) {
        //this.projectInstanceId = projectInstanceId;
        this.containerInstanceId = containerInstanceId;
        this.internalContainerName = internalContainerName;
        this.internalContainerId = internalContainerId;
        this.dateScheduled = dateScheduled;
        this.dateCreated = dateCreated;
        this.dateStarted = dateStarted;
        this.dateStopped = dateStopped;
        this.dateRemoved = dateRemoved;
        this.log = log;
        this.status = status;
        this.server = server;
        this.runConfiguration = runConfiguration;
    }

    public void setProjectInstanceId( ProjectInstanceId projectInstanceId ) {
        //this.projectInstanceId = projectInstanceId;
    }

    public void setContainerInstanceId( ContainerInstanceId containerInstanceId ) {
        this.containerInstanceId = containerInstanceId;
    }

    public void setInternalContainerName( String internalContainerName ) {
        this.internalContainerName = internalContainerName;
    }

    public void setInternalContainerId( String internalContainerId ) {
        this.internalContainerId = internalContainerId;
    }

    public void setDateScheduled( Date dateScheduled ) {
        this.dateScheduled = dateScheduled;
    }

    public void setDateCreated( Date dateCreated ) {
        this.dateCreated = dateCreated;
    }

    public void setDateStarted( Date dateStarted ) {
        this.dateStarted = dateStarted;
    }

    public void setDateStopped( Date dateStopped ) {
        this.dateStopped = dateStopped;
    }

    public void setDateRemoved( Date dateRemoved ) {
        this.dateRemoved = dateRemoved;
    }

    public void addLog( String log ) {
        this.log += log;
    }

    public void setStatus( ContainerInstanceStatus status ) {
        this.status = status;
    }

    public void setServer( ServerView server ) {
        this.server = server;
    }

    public void setRunConfiguration( RunConfigurationView runConfiguration ) {
        this.runConfiguration = runConfiguration;
    }

    public static class ContainerInstanceViewBuilder {

        private ProjectInstanceId projectInstanceId;
        private ContainerInstanceId containerInstanceId;
        private String internalContainerName;
        private String internalContainerId;
        private Date dateScheduled;
        private Date dateCreated;
        private Date dateStarted;
        private Date dateStopped;
        private Date dateRemoved;
        private String log;
        private ContainerInstanceStatus status;
        private ServerView server;
        private RunConfigurationView runConfiguration;

        public static ContainerInstanceViewBuilder aContainerInstanceView() {
            return new ContainerInstanceViewBuilder();
        }

        public ContainerInstanceViewBuilder withProjectInstanceId( ProjectInstanceId projectInstanceId ) {
            this.projectInstanceId = projectInstanceId;
            return this;
        }

        public ContainerInstanceViewBuilder withContainerInstanceId( ContainerInstanceId containerInstanceId ) {
            this.containerInstanceId = containerInstanceId;
            return this;
        }

        public ContainerInstanceViewBuilder withInternalContainerName( String internalContainerName ) {
            this.internalContainerName = internalContainerName;
            return this;
        }

        public ContainerInstanceViewBuilder withInternalContainerId( String internalContainerId ) {
            this.internalContainerId = internalContainerId;
            return this;
        }

        public ContainerInstanceViewBuilder withDateScheduled( Date dateScheduled ) {
            this.dateScheduled = dateScheduled;
            return this;
        }

        public ContainerInstanceViewBuilder withDateCreated( Date dateCreated ) {
            this.dateCreated = dateCreated;
            return this;
        }

        public ContainerInstanceViewBuilder withDateStarted( Date dateStarted ) {
            this.dateStarted = dateStarted;
            return this;
        }

        public ContainerInstanceViewBuilder withDateStopped( Date dateStopped ) {
            this.dateStopped = dateStopped;
            return this;
        }

        public ContainerInstanceViewBuilder withDateRemoved( Date dateRemoved ) {
            this.dateRemoved = dateRemoved;
            return this;
        }

        public ContainerInstanceViewBuilder withLog( String log ) {
            this.log = log;
            return this;
        }

        public ContainerInstanceViewBuilder withStatus( ContainerInstanceStatus status ) {
            this.status = status;
            return this;
        }

        public ContainerInstanceViewBuilder withServer( ServerView server ) {
            this.server = server;
            return this;
        }

        public ContainerInstanceViewBuilder withRunConfiguration( RunConfigurationView runConfiguration ) {
            this.runConfiguration = runConfiguration;
            return this;
        }

        public ContainerInstanceView build() {
            return new ContainerInstanceView( projectInstanceId,
                    containerInstanceId,
                    internalContainerName,
                    internalContainerId,
                    dateScheduled,
                    dateCreated,
                    dateStarted,
                    dateStopped,
                    dateRemoved,
                    log,
                    status,
                    server,
                    runConfiguration );
        }
    }
}
