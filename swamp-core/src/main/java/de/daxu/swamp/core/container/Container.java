package de.daxu.swamp.core.container;

import de.daxu.swamp.common.jpa.Identifiable;
import de.daxu.swamp.core.configuration.RunConfiguration;
import de.daxu.swamp.core.location.Location;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table( name = "container" )
@SuppressWarnings( "unused" )
public class Container extends Identifiable {

    @NotNull( message = "{NotNull.Container.runConfiguration}" )
    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn( name = "run_configuration_id" )
    private RunConfiguration runConfiguration;

    @ElementCollection( fetch = FetchType.EAGER )
    @CollectionTable( name = "container_aliases", joinColumns = @JoinColumn( name = "container_id" ) )
    private Set<String> aliases;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "container_location",
            joinColumns = @JoinColumn( name = "container_id", referencedColumnName = "id" ),
            inverseJoinColumns = @JoinColumn( name = "location_id", referencedColumnName = "id" ) )
    private Set<Location> potentialLocations;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinColumn( name = "container_id", referencedColumnName = "id" )
    private Set<PortMapping> portMappings;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinColumn( name = "container_id", referencedColumnName = "id" )
    private Set<EnvironmentVariable> environmentVariables;

    private Container() {
    }

    private Container( Set<String> aliases,
                       RunConfiguration runConfiguration,
                       Set<Location> potentialLocations,
                       Set<PortMapping> portMappings,
                       Set<EnvironmentVariable> environmentVariables ) {
        this.aliases = aliases;
        this.runConfiguration = runConfiguration;
        this.potentialLocations = potentialLocations;
        this.portMappings = portMappings;
        this.environmentVariables = environmentVariables;
    }

    public void setAliases( Set<String> aliases ) {
        this.aliases = aliases;
    }

    public void setRunConfiguration( RunConfiguration runConfiguration ) {
        this.runConfiguration = runConfiguration;
    }

    public void setPotentialLocations( Set<Location> potentialLocations ) {
        this.potentialLocations.clear();
        this.potentialLocations.addAll( potentialLocations );
    }

    public void setPortMappings( Set<PortMapping> portMappings ) {
        this.portMappings.clear();
        this.portMappings.addAll( portMappings );
    }

    public void setEnvironmentVariables( Set<EnvironmentVariable> environmentVariables ) {
        this.environmentVariables.clear();
        this.environmentVariables.addAll( environmentVariables );
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public RunConfiguration getRunConfiguration() {
        return runConfiguration;
    }

    public Set<Location> getPotentialLocations() {
        return potentialLocations;
    }

    public Set<PortMapping> getPortMappings() {
        return portMappings;
    }

    public Set<EnvironmentVariable> getEnvironmentVariables() {
        return environmentVariables;
    }

    public static class Builder {

        private Set<String> aliases;
        private RunConfiguration runConfiguration;
        private Set<Location> potentialLocations;
        private Set<PortMapping> portMappings;
        private Set<EnvironmentVariable> environmentVariables;

        public static Builder aContainer() {
            return new Builder();
        }

        public Builder withAliases( Set<String> aliases ) {
            this.aliases = aliases;
            return this;
        }

        public Builder withRunConfiguration( RunConfiguration runConfiguration ) {
            this.runConfiguration = runConfiguration;
            return this;
        }

        public Builder withPotentialLocations( Set<Location> potentialLocations ) {
            this.potentialLocations = potentialLocations;
            return this;
        }

        public Builder withPortMappings( Set<PortMapping> portMappings ) {
            this.portMappings = portMappings;
            return this;
        }

        public Builder withEnvironmentVariables( Set<EnvironmentVariable> environmentVariables ) {
            this.environmentVariables = environmentVariables;
            return this;
        }

        public Container build() {
            return new Container( aliases, runConfiguration, potentialLocations, portMappings, environmentVariables );
        }
    }
}
