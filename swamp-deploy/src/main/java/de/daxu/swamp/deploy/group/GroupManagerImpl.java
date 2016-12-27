package de.daxu.swamp.deploy.group;

import de.daxu.swamp.deploy.container.ContainerId;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Map.Entry;

public class GroupManagerImpl implements GroupManager {

    final Map<GroupId, Set<ContainerId>> groups = newHashMap();

    @Override
    public void addGroup( GroupId groupId ) {
        groups.put( groupId, newHashSet() );
    }

    @Override
    public void addContainer( GroupId groupId, ContainerId containerId ) {
        Set<ContainerId> containerIds = groups.getOrDefault( groupId, newHashSet() );
        containerIds.add( containerId );
        groups.put( groupId, containerIds );
    }

    @Override
    public Set<GroupId> getGroups( ContainerId containerId ) {
        return groups.entrySet()
                .stream()
                .filter( group -> group.getValue().contains( containerId ) )
                .map( Entry::getKey )
                .collect( Collectors.toSet() );
    }

    @Override
    public boolean exists( GroupId groupId ) {
        return groups.get( groupId ) != null;
    }

    @Override
    public Set<GroupId> getAllGroups() {
        return groups.keySet();
    }
}
