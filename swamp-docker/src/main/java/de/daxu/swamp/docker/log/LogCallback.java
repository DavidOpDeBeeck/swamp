package de.daxu.swamp.docker.log;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.util.function.Consumer;

public class LogCallback extends ResultCallbackTemplate<LogContainerResultCallback, Frame> {

    public static LogCallback withConsumer( final Consumer<String> consumer ) {
        return new LogCallback( consumer );
    }

    private Consumer<String> consumer;

    private LogCallback( Consumer<String> consumer ) {
        this.consumer = consumer;
    }

    @Override
    public void onNext( Frame frame ) {
        this.consumer.accept( decodeFrame( frame ) );
    }

    private String decodeFrame( Frame frame ) {
        return frame.toString()
                .replaceAll( "STDOUT: ", "\n" )
                .replaceAll( "STDERR: ", "\n" );
    }
}
