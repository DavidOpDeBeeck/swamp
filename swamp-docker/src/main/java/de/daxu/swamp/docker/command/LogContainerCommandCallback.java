package de.daxu.swamp.docker.command;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.util.function.Consumer;

public class LogContainerCommandCallback {

    public static CommandCallback<LogContainerResultCallback, Frame> onLogReceived(Consumer<String> logCallback) {
        return new CommandCallback.Builder<LogContainerResultCallback, Frame>()
                .withOnNextCallback(frame -> logCallback.accept(decodeFrame(frame)))
                .build();
    }

    private static String decodeFrame(Frame frame) {
        return frame.toString()
                .replaceAll("STDOUT: ", "\n")
                .replaceAll("STDERR: ", "\n");
    }
}
