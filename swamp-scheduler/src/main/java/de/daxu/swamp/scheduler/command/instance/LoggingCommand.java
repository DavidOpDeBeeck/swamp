package de.daxu.swamp.scheduler.command.instance;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import de.daxu.swamp.scheduler.core.ContainerInstance;
import de.daxu.swamp.scheduler.command.Command;
import de.daxu.swamp.scheduler.event.EventHandler;

import static de.daxu.swamp.scheduler.client.DockerClientFactory.createClient;


public class LoggingCommand extends Command<ContainerInstance> {

    public LoggingCommand( EventHandler eventHandler ) {
        super( eventHandler );
    }

    @Override
    public void execute( ContainerInstance instance ) {
        createClient( instance.getServer() )
                .logContainerCmd( instance.getInternalContainerId() )
                .withStdErr( true )
                .withStdOut( true )
                .withFollowStream( true )
                .exec( new LogSyncCallback( instance ) );
    }

    // TODO: possible memory leak when container is removed
    private class LogSyncCallback extends ResultCallbackTemplate<LogContainerResultCallback, Frame> {

        private ContainerInstance initialState;

        LogSyncCallback( ContainerInstance initialState ) {
            this.initialState = initialState;
        }

        @Override
        public void onNext( Frame object ) {
            initialState.addLog( object.toString().replaceAll( "STDOUT: ", "\n" ).replaceAll( "STDERR: ", "\n" ) );
            getEventHandler().onUpdate( initialState );
        }
    }
}
