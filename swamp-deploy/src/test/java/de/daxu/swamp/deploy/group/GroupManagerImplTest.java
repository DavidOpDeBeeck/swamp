package de.daxu.swamp.deploy.group;

import de.daxu.swamp.deploy.container.ContainerId;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupManagerImplTest {

    private GroupManagerImpl groupManager;

    @Before
    public void setUp() throws Exception {
        groupManager = new GroupManagerImpl();
    }

    @Test
    public void addContainer() throws Exception {
        GroupId groupId = GroupId.of( "1" );
        ContainerId containerId = ContainerId.of( "1" );

        groupManager.addContainerToGroup( groupId, containerId );

        assertThat( groupManager.groups.get( groupId ) )
                .containsExactly( containerId );
    }

    @Test
    public void exists() throws Exception {
        GroupId groupId = GroupId.of( "1" );
        groupManager.groups.put( groupId, newHashSet() );

        boolean exists = groupManager.exists( groupId );

        assertThat( exists ).isTrue();
    }
}