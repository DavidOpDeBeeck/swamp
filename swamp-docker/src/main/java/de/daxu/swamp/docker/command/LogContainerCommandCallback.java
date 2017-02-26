package de.daxu.swamp.docker.command;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.util.function.Consumer;

public class LogContainerCommandCallback extends CommandCallback<LogContainerResultCallback, Frame> {

    public static LogContainerCommandCallback onLogReceived(Consumer<String> logCallback) {
        return new LogContainerCommandCallback(frame -> logCallback.accept(decodeFrame(frame)));
    }

    private static String decodeFrame(Frame frame) {
        return frame.toString()
                .replaceAll("STDOUT: ", "\n")
                .replaceAll("STDERR: ", "\n");
    }

    private LogContainerCommandCallback(OnNextCallback<Frame> onNextDelegate) {
        super(onNextDelegate);
    }
}
