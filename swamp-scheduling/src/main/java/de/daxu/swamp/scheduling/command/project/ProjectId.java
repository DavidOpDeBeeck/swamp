package de.daxu.swamp.scheduling.command.project;

import de.daxu.swamp.common.cqrs.EntityId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AttributeOverride(name = "value", column = @Column(name = "project_id"))
public class ProjectId extends EntityId {

    public static ProjectId from(String id) {
        return new ProjectId(UUID.fromString(id));
    }

    public static ProjectId random() {
        return new ProjectId(UUID.randomUUID());
    }

    @SuppressWarnings("unused")
    private ProjectId() {
    }

    @SuppressWarnings("unused")
    private ProjectId(String projectId) {
        super(projectId);
    }

    private ProjectId(UUID projectId) {
        super(projectId);
    }
}
