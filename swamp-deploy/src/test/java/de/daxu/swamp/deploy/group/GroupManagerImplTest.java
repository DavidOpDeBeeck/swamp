package de.daxu.swamp.deploy.group;

import de.daxu.swamp.deploy.container.ContainerId;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupManagerImplTest {

    private GroupManagerImpl groupManager;

    @Before
    public void setUp() throws Exception {
        groupManager = new GroupManagerImpl();
    }

    @Test
    public void addGroup() throws Exception {
        GroupId groupId = GroupId.of( "1" );

        groupManager.addGroup( groupId );

        assertThat( groupManager.groups.keySet() )
                .containsExactly( groupId );
    }

    @Test
    public void addGroupMetaData() throws Exception {
        GroupId groupId = GroupId.of( "1" );

        groupManager.addGroup( groupId );
        groupManager.addGroupMetaData( groupId, "testKey", "testValue" );

        Map<String, String> actual = groupManager.groupMetaData.get( groupId );
        assertThat( actual ).isNotNull();
        assertThat( actual.get( "testKey" ) ).isEqualTo( "testValue" );
    }

    @Test
    public void getGroupMetaData() throws Exception {
        GroupId groupId = GroupId.of( "1" );
        groupManager.groups.put( groupId, newHashSet() );
        groupManager.groupMetaData.put( groupId, newHashMap() );
        groupManager.groupMetaData.get( groupId ).put( "testKey", "testValue" );

        String metaData = groupManager.getGroupMetaData( groupId, "testKey" );

        assertThat( metaData ).isEqualTo( "testValue" );
    }

    @Test
    public void addContainer() throws Exception {
        GroupId groupId = GroupId.of( "1" );
        ContainerId containerId = ContainerId.of( "1" );

        groupManager.addContainer( groupId, containerId );

        assertThat( groupManager.groups.get( groupId ) )
                .containsExactly( containerId );
    }

    @Test
    public void getGroups() throws Exception {
        GroupId groupId = GroupId.of( "1" );
        ContainerId containerId = ContainerId.of( "1" );
        groupManager.groups.put( groupId, newHashSet( containerId ) );

        Set<GroupId> groups = groupManager.getGroups( containerId );

        assertThat( groups )
                .containsExactly( groupId );
    }

    @Test
    public void exists() throws Exception {
        GroupId groupId = GroupId.of( "1" );
        groupManager.groups.put( groupId, newHashSet() );

        boolean exists = groupManager.exists( groupId );

        assertThat( exists ).isTrue();
    }

    @Test
    public void getAllGroups() throws Exception {
        GroupId groupId1 = GroupId.of( "1" );
        GroupId groupId2 = GroupId.of( "2" );
        groupManager.groups.put( groupId1, newHashSet() );
        groupManager.groups.put( groupId2, newHashSet() );

        Set<GroupId> groups = groupManager.getAllGroups();

        assertThat( groups ).isNotEmpty();
        assertThat( groups ).containsExactlyInAnyOrder(
                groupId1, groupId2
        );
    }
}