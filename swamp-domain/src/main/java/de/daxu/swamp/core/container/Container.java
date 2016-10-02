package de.daxu.swamp.core.container;

import de.daxu.swamp.common.Identifiable;
import de.daxu.swamp.core.container.configuration.RunConfiguration;
import de.daxu.swamp.core.location.Location;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "container" )
public class Container extends Identifiable {

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinColumn( name = "run_configuration_id" )
    private RunConfiguration runConfiguration;

    @ManyToMany
    @JoinTable(
            name = "container_location",
            joinColumns = @JoinColumn( name = "container_id", referencedColumnName = "id" ),
            inverseJoinColumns = @JoinColumn( name = "location_id", referencedColumnName = "id" ) )
    private List<Location> potentialLocations;

    public Container() {
    }

    public Container( RunConfiguration runConfiguration, List<Location> potentialLocations ) {
        this.runConfiguration = runConfiguration;
        this.potentialLocations = potentialLocations;
    }

    public RunConfiguration getRunConfiguration() {
        return runConfiguration;
    }

    public List<Location> getPotentialLocations() {
        return potentialLocations;
    }

    public static class ContainerBuilder {

        private RunConfiguration runConfiguration;
        private List<Location> potentialLocations;

        public static ContainerBuilder containerBuilder() {
            return new ContainerBuilder();
        }

        public ContainerBuilder withRunConfiguration( RunConfiguration runConfiguration ) {
            this.runConfiguration = runConfiguration;
            return this;
        }

        public ContainerBuilder withPotentialLocations( List<Location> potentialLocations ) {
            this.potentialLocations = potentialLocations;
            return this;
        }

        public Container build() {
            return new Container( runConfiguration, potentialLocations );
        }
    }
}
