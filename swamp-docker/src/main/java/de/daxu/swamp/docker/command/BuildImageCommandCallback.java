package de.daxu.swamp.docker.command;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.core.command.BuildImageResultCallback;

public class BuildImageCommandCallback extends CommandCallback<BuildImageResultCallback, BuildResponseItem> {

    public static ResultCallback<BuildResponseItem> onImageBuild(OnCompletedCallback onCompletedCallback) {
        return new CommandCallback<BuildImageResultCallback, BuildResponseItem>(onCompletedCallback);
    }

    private BuildImageCommandCallback(OnCompletedCallback onCompletedCallback) {
        super(onCompletedCallback);
    }
}
