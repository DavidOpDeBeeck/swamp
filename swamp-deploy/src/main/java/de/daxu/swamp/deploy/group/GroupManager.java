package de.daxu.swamp.deploy.group;

public interface GroupManager {

    Group getOrCreate(GroupId groupId);

    boolean exists(GroupId groupId);

}
