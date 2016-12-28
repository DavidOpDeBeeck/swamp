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
    final Map<GroupId, Map<String, String>> groupMetaData = newHashMap();

    @Override
    public void addGroup( GroupId groupId ) {
        groups.putIfAbsent( groupId, newHashSet() );
        groupMetaData.putIfAbsent( groupId, newHashMap() );
    }

    @Override
    public void addGroupMetaData( GroupId groupId, String key, String value ) {
        addGroup( groupId );
        groupMetaData.get( groupId ).put( key, value );
    }

    @Override
    public String getGroupMetaData( GroupId groupId, String key ) {
        Map<String, String> metaData = groupMetaData.get( groupId );
        if( metaData == null )
            return null;
        return metaData.get( key );
    }

    @Override
    public void addContainer( GroupId groupId, ContainerId containerId ) {
        addGroup( groupId );
        groups.get( groupId ).add( containerId );
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
