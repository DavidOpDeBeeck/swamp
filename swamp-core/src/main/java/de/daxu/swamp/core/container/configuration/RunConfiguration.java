package de.daxu.swamp.core.container.configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import de.daxu.swamp.common.jpa.Identifiable;

import javax.persistence.*;

@Entity
@Table( name = "run_configuration" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "type" )
public abstract class RunConfiguration extends Identifiable {

    public abstract CreateContainerCmd execute( DockerClient client ); // TODO: rework

    public abstract RunConfigurationType getType();
}
