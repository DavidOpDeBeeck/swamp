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

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL , orphanRemoval = true )
    @JoinColumn( name = "run_configuration_id" )
    private RunConfiguration runConfiguration;

    @Column( name = "name" )
    private String name;

    @ManyToMany
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

    private Container( String name, RunConfiguration runConfiguration, Set<Location> potentialLocations, Set<PortMapping> portMappings, Set<EnvironmentVariable> environmentVariables ) {
        this.name = name;
        this.runConfiguration = runConfiguration;
        this.potentialLocations = potentialLocations;
        this.portMappings = portMappings;
        this.environmentVariables = environmentVariables;
    }

    public void setName( String name ) {
        this.name = name;
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

    public String getName() {
        return name;
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

        private String name;
        private RunConfiguration runConfiguration;
        private Set<Location> potentialLocations = new HashSet<>();
        private Set<PortMapping> portMappings = new HashSet<>();
        private Set<EnvironmentVariable> environmentVariables = new HashSet<>();

        public static ContainerBuilder aContainer() {
            return new ContainerBuilder();
        }

        public ContainerBuilder withName( String name ) {
            this.name = name;
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
            return new Container( name, runConfiguration, potentialLocations, portMappings, environmentVariables );
        }
    }
}
