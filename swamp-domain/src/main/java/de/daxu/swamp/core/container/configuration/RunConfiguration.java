package de.daxu.swamp.core.container.configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import de.daxu.swamp.common.Identifiable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "run_configuration" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "type" )
public abstract class RunConfiguration extends Identifiable {

    public abstract CreateContainerCmd execute( DockerClient client );

    public abstract RunConfigurationType getType();
}
