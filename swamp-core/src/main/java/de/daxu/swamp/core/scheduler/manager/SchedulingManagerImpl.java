package de.daxu.swamp.core.scheduler.manager;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.SyncDockerCmd;
import de.daxu.swamp.common.util.Pair;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.scheduler.ContainerInstance;
import de.daxu.swamp.core.scheduler.Scheduler;
import de.daxu.swamp.core.scheduler.SchedulerImpl;
import de.daxu.swamp.core.scheduler.command.container.CreateCommand;
import de.daxu.swamp.core.scheduler.command.instance.ContainerInstanceCommand;
import de.daxu.swamp.core.scheduler.strategy.SchedulingStrategy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import static de.daxu.swamp.core.scheduler.ContainerInstance.ContainerInstanceBuilder.aContainerInstance;
import static de.daxu.swamp.core.scheduler.command.CommandFactory.createCommand;
import static de.daxu.swamp.core.scheduler.command.CommandFactory.startCommand;

@Component
public class SchedulingManagerImpl implements SchedulingManager {

    private Scheduler scheduler;
    private Map<Project, Collection<ContainerInstance>> projectsMap;
    private Queue<Pair<Container, CreateCommand>> createQueue = new ArrayBlockingQueue<>( 255 );
    private Queue<Pair<ContainerInstance, ContainerInstanceCommand>> commandQueue = new ArrayBlockingQueue<>( 255 );

    private SchedulingManagerImpl() {
        scheduler = new SchedulerImpl();
        projectsMap = new HashMap<>();
    }

    @Override
    public void schedule( Project project, SchedulingStrategy strategy ) {
        strategy.createSchedule( project.getContainers() )
                .forEach( ( container, server ) -> createQueue.add( Pair.of( container, createCommand( server ) ) ) );
    }

    @Override
    public void schedule( ContainerInstance instance, ContainerInstanceCommand command ) {
        commandQueue.add( Pair.of( instance, command ) );
    }

    @Scheduled( fixedDelay = 5000 ) // TODO: write own scheduler
    private void createContainer() {
        if (createQueue.peek() == null) return;

        Pair<Container, CreateCommand> pair = createQueue.remove();
        Container container = pair.getFirst();
        CreateCommand command = pair.getSecond();

        CreateContainerCmd dockerCmd = command.execute( container );
        CreateContainerResponse response = dockerCmd.exec();

        ContainerInstance instance = aContainerInstance()
                .withInternalContainerId( response.getId() )
                .withServer( command.getServer() )
                .withContainer( container )
                .withStartDate( new Date() )
                .build();

        // TODO: add logging command

        commandQueue.add( Pair.of( instance, startCommand() ) );
    }

    @Scheduled( fixedDelay = 5000 ) // TODO: write own scheduler
    private void executeCommands() {
        if (commandQueue.peek() == null) return;

        Pair<ContainerInstance, ContainerInstanceCommand> pair = commandQueue.remove();
        ContainerInstance instance = pair.getFirst();
        ContainerInstanceCommand command = pair.getSecond();

        SyncDockerCmd dockerCmd = command.execute( instance );
        dockerCmd.exec();
    }

    @Override
    public Collection<Project> getProjects() {
        return projectsMap.keySet();
    }

    @Override
    public Collection<ContainerInstance> getInstances( Project project ) {
        return projectsMap.get( project );
    }
}
