package de.daxu.swamp.deploy.group;

import java.util.Optional;

public class GroupService {

    private GroupDAO dao = new GroupDAO();

    public Group getOrCreate(Group group) {
        Optional<Group> optionalGroup = dao.get(group.getGroupId());
        return optionalGroup.orElse(dao.create(group));
    }

    public boolean exists(GroupId groupId) {
        return dao.exists(groupId);
    }
}
