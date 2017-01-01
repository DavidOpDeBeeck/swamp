package de.daxu.swamp.deploy.group;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class GroupManagerImpl implements GroupManager {

    final Map<GroupId, Set<ContainerId>> groups = newHashMap();

    @Override
    public void addContainerToGroup( GroupId groupId, ContainerId containerId ) {
        groups.putIfAbsent( groupId, newHashSet() );
        groups.get( groupId ).add( containerId );
    }

    @Override
    public boolean exists( GroupId groupId ) {
        return groups.get( groupId ) != null;
    }
}
