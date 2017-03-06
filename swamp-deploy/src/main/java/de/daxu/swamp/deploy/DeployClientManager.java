package de.daxu.swamp.deploy;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.group.GroupService;

public interface DeployClientManager<T extends DeployClient> {

    T createClient(Server server);

    GroupService getGroupService();

}
