package de.daxu.swamp.scheduling.command.build;


import de.daxu.swamp.common.cqrs.EntityId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "build_id"))
public class BuildId extends EntityId {

    public static BuildId from(String id) {
        return new BuildId(UUID.fromString(id));
    }

    public static BuildId random() {
        return new BuildId(UUID.randomUUID());
    }

    @SuppressWarnings("unused")
    private BuildId() {
    }

    @SuppressWarnings("unused")
    private BuildId(String buildId) {
        super(buildId);
    }

    private BuildId(UUID buildId) {
        super(buildId);
    }
}
