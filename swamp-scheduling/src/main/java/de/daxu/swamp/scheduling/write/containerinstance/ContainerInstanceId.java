package de.daxu.swamp.scheduling.write.containerinstance;


import java.io.Serializable;
import java.util.UUID;

public class ContainerInstanceId implements Serializable {

    private String containerInstanceId;

    private ContainerInstanceId( String containerInstanceId ) {
        this.containerInstanceId = containerInstanceId;
    }

    public static ContainerInstanceId random() {
        return new ContainerInstanceId( UUID.randomUUID().toString() );
    }

    public static ContainerInstanceId from( String id ) {
        return new ContainerInstanceId( id );
    }

    public String getContainerInstanceId() {
        return containerInstanceId;
    }

    @Override
    public String toString() {
        return containerInstanceId;
    }
}
