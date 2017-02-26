package de.daxu.swamp.deploy.group;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupManagerImplTest {

    private GroupManagerImpl groupManager;

    @Before
    public void setUp() throws Exception {
        groupManager = new GroupManagerImpl();
    }

    @Test
    public void create() throws Exception {
        GroupId groupId = GroupId.of(UUID.randomUUID().toString());

        Group group = groupManager.create(groupId);

        assertThat(groupManager.groups)
                .containsExactly(group);
    }

    @Test
    public void get() throws Exception {
        GroupId groupId = GroupId.of(UUID.randomUUID().toString());
        Group expected = Group.withGroupId(groupId);
        groupManager.groups.add(expected);

        Group actual = groupManager.get(groupId);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    public void get_null() throws Exception {
        GroupId groupId = GroupId.of(UUID.randomUUID().toString());

        Group actual = groupManager.get(groupId);

        assertThat(actual).isNull();
    }

    @Test
    public void exists() throws Exception {
        GroupId groupId = GroupId.of(UUID.randomUUID().toString());
        Group expected = Group.withGroupId(groupId);
        groupManager.groups.add(expected);

        boolean exists = groupManager.exists(groupId);

        assertThat(exists).isTrue();
    }

    @Test
    public void exists_false() throws Exception {
        GroupId groupId = GroupId.of(UUID.randomUUID().toString());

        boolean exists = groupManager.exists(groupId);

        assertThat(exists).isFalse();
    }
}