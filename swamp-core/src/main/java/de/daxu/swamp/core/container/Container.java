package de.daxu.swamp.core.container;

import de.daxu.swamp.common.jpa.Identifiable;
import de.daxu.swamp.core.container.configuration.RunConfiguration;
import de.daxu.swamp.core.location.Location;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "container" )
@SuppressWarnings( "unused" )
public class Container extends Identifiable {

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinColumn( name = "run_configuration_id" )
    private RunConfiguration runConfiguration;

    @Column( name = "arguments", unique = true )
    private String arguments;

    @ManyToMany
    @JoinTable(
            name = "container_location",
            joinColumns = @JoinColumn( name = "container_id", referencedColumnName = "id" ),
            inverseJoinColumns = @JoinColumn( name = "location_id", referencedColumnName = "id" ) )
    private Set<Location> potentialLocations;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinColumn( name = "container_id", referencedColumnName = "id", nullable = false )
    private Set<PortMapping> portMappings;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinColumn( name = "container_id", referencedColumnName = "id", nullable = false )
    private Set<EnvironmentVariable> environmentVariables;

    private Container() {
    }

    private Container( String arguments, RunConfiguration runConfiguration, Set<Location> potentialLocations, Set<PortMapping> portMappings, Set<EnvironmentVariable> environmentVariables ) {
        this.arguments = arguments;
        this.runConfiguration = runConfiguration;
        this.potentialLocations = potentialLocations;
        this.portMappings = portMappings;
        this.environmentVariables = environmentVariables;
    }

    public void setArguments( String arguments ) {
        this.arguments = arguments;
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

    public String getArguments() {
        return arguments;
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

    public static class ContainerBuilder {

        private String arguments;
        private RunConfiguration runConfiguration;
        private Set<Location> potentialLocations = new HashSet<>();
        private Set<PortMapping> portMappings = new HashSet<>();
        private Set<EnvironmentVariable> environmentVariables = new HashSet<>();

        public static ContainerBuilder aContainer() {
            return new ContainerBuilder();
        }

        public ContainerBuilder withArguments( String arguments ) {
            this.arguments = arguments;
            return this;
        }

        public ContainerBuilder withRunConfiguration( RunConfiguration runConfiguration ) {
            this.runConfiguration = runConfiguration;
            return this;
        }

        public ContainerBuilder withPotentialLocations( Set<Location> potentialLocations ) {
            this.potentialLocations = potentialLocations;
            return this;
        }

        public ContainerBuilder withPortMappings( Set<PortMapping> portMappings ) {
            this.portMappings = portMappings;
            return this;
        }

        public ContainerBuilder withEnvironmentVariables( Set<EnvironmentVariable> environmentVariables ) {
            this.environmentVariables = environmentVariables;
            return this;
        }

        public Container build() {
            return new Container( arguments, runConfiguration, potentialLocations, portMappings, environmentVariables );
        }
    }
}
