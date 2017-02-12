package de.daxu.swamp.scheduling.resource.build;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.query.build.BuildQueryService;
import de.daxu.swamp.scheduling.query.build.BuildView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BuildViewParamConverter implements Converter<String, BuildView> {

    private final BuildQueryService buildQueryService;

    @Autowired
    public BuildViewParamConverter(BuildQueryService buildQueryService) {
        this.buildQueryService = buildQueryService;
    }

    @Override
    public BuildView convert(String source) {
        BuildId id = BuildId.from(source);
        BuildView view = buildQueryService.getBuildViewById(id);

        if(view == null)
            throw new NotFoundException("Build was not found!");

        return view;
    }
}
