package de.daxu.swamp.docker;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployClientManager;
import de.daxu.swamp.deploy.group.GroupService;
import de.daxu.swamp.docker.behaviour.DockerBehaviour;
import de.daxu.swamp.docker.behaviour.DockerBehaviourFactory;
import de.daxu.swamp.docker.command.DockerCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DockerClientManager implements DeployClientManager<DockerClient> {

    private final GroupService groupService;
    private final DockerBehaviourFactory dockerBehaviourFactory;

    @Autowired
    public DockerClientManager(GroupService groupService,
                               DockerBehaviourFactory dockerBehaviourFactory) {
        this.groupService = groupService;
        this.dockerBehaviourFactory = dockerBehaviourFactory;
    }

    @Override
    public DockerClient createClient(Server server) {
        DockerBehaviour behaviour = dockerBehaviourFactory.createBehaviour(server);
        return new DockerClient(groupService, new DockerCommandExecutor(behaviour));
    }

    @Override
    public GroupService getGroupService() {
        return groupService;
    }
}
