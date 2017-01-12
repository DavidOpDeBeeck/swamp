package de.daxu.swamp.docker.configurator;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import de.daxu.swamp.core.configuration.DockerfileConfiguration;
import de.daxu.swamp.core.configuration.GitConfiguration;
import de.daxu.swamp.core.configuration.ImageConfiguration;
import de.daxu.swamp.core.configuration.RunConfigurator;
import org.apache.commons.lang.NotImplementedException;

public class DockerRunConfigurator implements RunConfigurator<CreateContainerCmd> {

    private final DockerClient client;

    public DockerRunConfigurator( DockerClient client ) {
        this.client = client;
    }

    @Override
    public CreateContainerCmd configure( GitConfiguration configuration ) {
        throw new NotImplementedException();
    }

    @Override
    public CreateContainerCmd configure( ImageConfiguration configuration ) {
        return client.createContainerCmd( configuration.getName() );
    }

    @Override
    public CreateContainerCmd configure( DockerfileConfiguration configuration ) {
        throw new NotImplementedException();
    }
}
