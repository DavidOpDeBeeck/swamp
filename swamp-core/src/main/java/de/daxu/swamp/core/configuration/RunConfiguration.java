package de.daxu.swamp.core.configuration;

import de.daxu.swamp.common.Identifiable;

import javax.persistence.*;

@Entity
@Table( name = "run_configuration" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "type" )
public abstract class RunConfiguration extends Identifiable {

    public abstract <T> T configure( RunConfigurator<T> configurator );

    public abstract RunConfigurationType getType();
}
