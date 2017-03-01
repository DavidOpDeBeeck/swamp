package de.daxu.swamp.deploy.group;

import de.daxu.swamp.common.cqrs.EntityId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "group_id"))
public class GroupId extends EntityId {

    public static GroupId of(String id) {
        return new GroupId(UUID.fromString(id));
    }

    @SuppressWarnings("unused")
    private GroupId() {
    }

    private GroupId(UUID groupId) {
        super(groupId);
    }
}
