package de.daxu.swamp.deploy.group;

import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupDAOTest {

    private GroupDAO dao = new GroupDAO();

    @Test
    public void create() throws Exception {
        GroupId groupId = GroupId.of(UUID.randomUUID().toString());
        Group group = Group.withGroupId(groupId);

        dao.create(group);

        assertThat(dao.groups).containsExactly(group);
    }

    @Test
    public void remove() throws Exception {
        GroupId groupId = GroupId.of(UUID.randomUUID().toString());
        Group group = Group.withGroupId(groupId);
        dao.groups.add(group);

        dao.remove(groupId);

        assertThat(dao.groups).isEmpty();
    }

    @Test
    public void get() throws Exception {
        GroupId groupId = GroupId.of(UUID.randomUUID().toString());
        Group expected = Group.withGroupId(groupId);
        dao.groups.add(expected);

        Optional<Group> actual = dao.get(groupId);

        assertThat(actual).isPresent();
        assertThat(actual).contains(expected);
    }

    @Test
    public void exists() throws Exception {
        GroupId groupId = GroupId.of(UUID.randomUUID().toString());
        Group expected = Group.withGroupId(groupId);
        dao.groups.add(expected);

        boolean exists = dao.exists(groupId);

        assertThat(exists).isTrue();
    }
}