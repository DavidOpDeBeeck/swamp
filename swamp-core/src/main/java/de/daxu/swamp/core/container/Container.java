package de.daxu.swamp.core.container;

import de.daxu.swamp.common.Identifiable;
import de.daxu.swamp.core.container.configuration.RunConfiguration;
import de.daxu.swamp.core.location.Location;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "container" )
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
    private List<Location> potentialLocations;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinColumn( name = "container_id", referencedColumnName = "id", nullable = false )
    private List<PortMapping> portMappings;

    private Container() {
    }

    private Container( String arguments, RunConfiguration runConfiguration, List<Location> potentialLocations, List<PortMapping> portMappings ) {
        this.arguments = arguments;
        this.runConfiguration = runConfiguration;
        this.potentialLocations = potentialLocations;
        this.portMappings = portMappings;
    }

    public void setArguments( String arguments ) {
        this.arguments = arguments;
    }

    public void setRunConfiguration( RunConfiguration runConfiguration ) {
        this.runConfiguration = runConfiguration;
    }

    public void setPotentialLocations( List<Location> potentialLocations ) {
        this.potentialLocations = potentialLocations;
    }

    public void setPortMappings( List<PortMapping> portMappings ) {
        this.portMappings.clear();
        this.portMappings.addAll( portMappings );
    }

    public String getArguments() {
        return arguments;
    }

    public RunConfiguration getRunConfiguration() {
        return runConfiguration;
    }

    public List<Location> getPotentialLocations() {
        return potentialLocations;
    }

    public List<PortMapping> getPortMappings() {
        return portMappings;
    }

    public static class ContainerBuilder {

        private String arguments;
        private RunConfiguration runConfiguration;
        private List<Location> potentialLocations;
        private List<PortMapping> portMappings;

        public static ContainerBuilder aContainer() {
            return new ContainerBuilder();
        }

        private ContainerBuilder() {
            this.potentialLocations = new ArrayList<>();
            this.portMappings = new ArrayList<>();
        }

        public ContainerBuilder withArguments( String arguments ) {
            this.arguments = arguments;
            return this;
        }

        public ContainerBuilder withRunConfiguration( RunConfiguration runConfiguration ) {
            this.runConfiguration = runConfiguration;
            return this;
        }

        public ContainerBuilder withPotentialLocations( List<Location> potentialLocations ) {
            this.potentialLocations = potentialLocations;
            return this;
        }

        public ContainerBuilder withPortMappings( List<PortMapping> portMappings ) {
            this.portMappings = portMappings;
            return this;
        }

        public Container build() {
            return new Container( arguments, runConfiguration, potentialLocations, portMappings );
        }
    }
}
