package de.daxu.swamp.core.configuration;

public interface RunConfigurator<T> {

    T configure( GitConfiguration configuration );

    T configure( ImageConfiguration configuration );

    T configure( DockerfileConfiguration configuration );
}
