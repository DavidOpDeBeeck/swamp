package de.daxu.swamp.scheduler.service;

import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.ProjectInstance;
import de.daxu.swamp.scheduler.repository.SchedulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Override
    public void addProjectInstance( ProjectInstance projectInstance ) {
        schedulingRepository.addProjectInstance( projectInstance );
    }

    @Override
    public void updateProjectInstance( ProjectInstance projectInstance ) {
        schedulingRepository.updateProjectInstance( projectInstance );
    }

    @Override
    public ProjectInstance getProjectInstance( String id ) {
        return schedulingRepository.getProjectInstance( id );
    }

    @Override
    public Set<ProjectInstance> getAllProjectInstances() {
        return schedulingRepository.getAllProjectInstances();
    }

    @Override
    public ContainerInstance getContainerInstanceByInternalId( String internalId ) {
        return schedulingRepository.getContainerInstanceByInternalId( internalId );
    }
}
