package de.daxu.swamp.api.replay;

import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.scheduling.replay.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static de.daxu.swamp.api.replay.ReplayResource.REPLAY_URL;

@RestController
@RequestMapping(REPLAY_URL)
public class ReplayResource {

    final static String REPLAY_URL = "/replay";

    private final ResponseFactory response;
    private final ReplayService replayService;

    @Autowired
    public ReplayResource(ResponseFactory response, ReplayService replayService) {
        this.response = response;
        this.replayService = replayService;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public Response replay() {
        replayService.clearViews();
        replayService.startReplay();
        return response.success();
    }
}
