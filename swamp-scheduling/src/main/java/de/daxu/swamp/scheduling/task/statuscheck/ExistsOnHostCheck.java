package de.daxu.swamp.scheduling.task.statuscheck;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;

public class ExistsOnHostCheck extends HostCheck {

    public ExistsOnHostCheck( Server server, DeployFacade deployFacade ) {
        super( server, deployFacade );
    }

    @Override
    public boolean check( ContainerInstanceView containerInstanceView ) {
        return getContainerClient().exists( containerInstanceView.getContainerId() );
    }
}
