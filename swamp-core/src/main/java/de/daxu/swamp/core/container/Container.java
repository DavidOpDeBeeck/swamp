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

    @Column( name = "arguments", unique = true )
    private String arguments;

    @ManyToMany
    @JoinTable(
            name = "container_location",
            joinColumns = @JoinColumn( name = "container_id", referencedColumnName = "id" ),
            inverseJoinColumns = @JoinColumn( name = "location_id", referencedColumnName = "id" ) )
    private List<Location> potentialLocations;

    private Container() {
    }

    Container( String arguments, RunConfiguration runConfiguration, List<Location> potentialLocations ) {
        this.arguments = arguments;
        this.runConfiguration = runConfiguration;
        this.potentialLocations = potentialLocations;
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

    public String getArguments() {
        return arguments;
    }

    public RunConfiguration getRunConfiguration() {
        return runConfiguration;
    }

    public List<Location> getPotentialLocations() {
        return potentialLocations;
    }

    public static class ContainerBuilder {

        private String arguments;
        private RunConfiguration runConfiguration;
        private List<Location> potentialLocations;

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

        public ContainerBuilder withPotentialLocations( List<Location> potentialLocations ) {
            this.potentialLocations = potentialLocations;
            return this;
        }

        public Container build() {
            return new Container( arguments, runConfiguration, potentialLocations );
        }
    }
}
