package de.daxu.swamp.deploy.group;

import de.daxu.swamp.deploy.container.ContainerId;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupManagerImplTest {

    private static final GroupId GROUP_ID = GroupId.of(UUID.randomUUID().toString());
    private static final ContainerId CONTAINER_ID = ContainerId.of(UUID.randomUUID().toString());
    private static final ContainerId ANOTHER_CONTAINER_ID = ContainerId.of(UUID.randomUUID().toString());

    private GroupManagerImpl groupManager;

    @Before
    public void setUp() throws Exception {
        groupManager = new GroupManagerImpl();
    }

    @Test
    public void getOrCreate_IfMissingAddsTheGroup() throws Exception {
        Group group = groupManager.getOrCreate(GROUP_ID);

        assertThat(groupManager.groups).containsExactly(group);
    }

    @Test
    public void getOrCreate_IfPresentReturnsTheGroup() throws Exception {
        Group expected = group();
        groupManager.groups.add(expected);

        Group actual = groupManager.getOrCreate(expected.getGroupId());

        assertThat(expected)
                .isEqualToComparingFieldByField(actual);
    }

    @Test
    public void exists() throws Exception {
        Group expected = group();
        groupManager.groups.add(expected);

        boolean exists = groupManager.exists(expected.getGroupId());

        assertThat(exists).isTrue();
    }

    @Test
    public void exists_false() throws Exception {
        boolean exists = groupManager.exists(GROUP_ID);

        assertThat(exists).isFalse();
    }

    private Group group() {
        return new Group.Builder()
                .withGroupId(GROUP_ID)
                .withContainers(newHashSet(CONTAINER_ID, ANOTHER_CONTAINER_ID))
                .build();
    }
}