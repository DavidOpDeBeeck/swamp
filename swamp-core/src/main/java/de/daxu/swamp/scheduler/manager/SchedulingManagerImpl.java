package de.daxu.swamp.scheduler.manager;

import de.daxu.swamp.common.util.Pair;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.action.Action;
import de.daxu.swamp.scheduler.action.ActionFactory;
import de.daxu.swamp.scheduler.command.Command;
import de.daxu.swamp.scheduler.command.CommandFactory;
import de.daxu.swamp.scheduler.command.container.CreateCommand;
import de.daxu.swamp.scheduler.event.EventHandler;
import de.daxu.swamp.scheduler.event.EventListener;
import de.daxu.swamp.scheduler.repository.SchedulingRepository;
import de.daxu.swamp.scheduler.strategy.SchedulingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class SchedulingManagerImpl implements SchedulingManager, EventListener {

    @Autowired
    private EventHandler eventHandler;

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private CommandFactory commandFactory;

    @Autowired
    private ActionFactory actionFactory;

    private Set<Action> actions = new HashSet<>();

    private Queue<Pair<Container, CreateCommand>> createQueue = new ArrayBlockingQueue<>( 255 );
    private Queue<Pair<ContainerInstance, Command<ContainerInstance>>> commandQueue = new ArrayBlockingQueue<>( 255 );

    @PostConstruct
    private void setUp() {
        eventHandler.register( this );
        actions.add( actionFactory.clearNotManagedContainers() );
        actions.add( actionFactory.syncContainerInstanceAction() );
    }

    @Override
    public void schedule( Project project, SchedulingStrategy strategy ) {
        strategy.createSchedule( project.getContainers() )
                .forEach( ( container, server ) -> {
                    schedulingRepository.addContainerToProject( project, container );
                    createQueue.add( Pair.of( container, commandFactory.createCommand( server ) ) );
                } );
    }

    @Override
    public void schedule( ContainerInstance instance, Command command ) {
        commandQueue.add( Pair.of( instance, command ) );
    }

    @Override
    public Set<Project> getProjects() {
        return schedulingRepository.getAllProjects();
    }

    @Override
    public ContainerInstance getInstance( String internalId ) {
        return schedulingRepository.getInstance( internalId );
    }

    @Override
    public Set<ContainerInstance> getAllInstances() {
        return schedulingRepository.getAllInstances();
    }

    @Override
    public ContainerInstance getInstance( Container container ) {
        return schedulingRepository.getInstance( container );
    }

    @Scheduled( fixedDelay = 5000 ) // TODO: write own scheduler
    private void createContainer() {
        if ( createQueue.peek() == null ) return;

        Pair<Container, CreateCommand> pair = createQueue.remove();
        Container container = pair.getFirst();
        CreateCommand command = pair.getSecond();

        command.execute( container );
    }

    @Scheduled( fixedDelay = 5000 ) // TODO: write own scheduler
    private void executeCommands() {
        if ( commandQueue.peek() == null ) return;

        Pair<ContainerInstance, Command<ContainerInstance>> pair = commandQueue.remove();
        ContainerInstance instance = pair.getFirst();
        Command<ContainerInstance> command = pair.getSecond();

        command.execute( instance );
    }

    @Scheduled( fixedDelay = 5000 ) // TODO: write own scheduler
    private void executeActions() {
        actions.forEach( Action::execute );
    }

    @Override
    public void onCreate( ContainerInstance instance ) {
        schedulingRepository.addInstance( instance );
        commandQueue.add( Pair.of( instance, commandFactory.startCommand() ) );
        commandQueue.add( Pair.of( instance, commandFactory.logCommand() ) );
    }

    @Override
    public void onUpdate( ContainerInstance instance ) {
        schedulingRepository.updateInstance( instance );
    }

    @Override
    public void onDelete( ContainerInstance instance ) {
        schedulingRepository.removeInstance( instance );
    }
}
