package de.daxu.swamp.core.containertemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerTemplateService {

    private final ContainerTemplateRepository containerTemplateRepository;

    @Autowired
    public ContainerTemplateService(ContainerTemplateRepository containerTemplateRepository) {
        this.containerTemplateRepository = containerTemplateRepository;
    }

    public ContainerTemplate updateContainerTemplate(ContainerTemplate containerTemplate) {
        return containerTemplateRepository.save(containerTemplate);
    }

    public ContainerTemplate findContainerTemplate(String id) {
        return containerTemplateRepository.findOne(id);
    }
}
