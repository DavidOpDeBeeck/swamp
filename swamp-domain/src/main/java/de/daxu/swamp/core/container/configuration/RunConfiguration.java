package de.daxu.swamp.core.container.configuration;

import de.daxu.swamp.common.Identifiable;

import javax.persistence.*;

@Entity
@Table( name = "run_configuration" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "type" )
public abstract class RunConfiguration extends Identifiable {

    public abstract void run();

    public abstract RunConfigurationType getType();
}
