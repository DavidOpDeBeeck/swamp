package de.daxu.swamp.deploy.group;

public interface GroupManager {

    Group create(GroupId groupId);

    Group get(GroupId groupId);

    boolean exists(GroupId groupId);

}
