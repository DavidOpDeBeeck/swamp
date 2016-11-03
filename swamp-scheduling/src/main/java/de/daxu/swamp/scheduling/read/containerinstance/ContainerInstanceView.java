package de.daxu.swamp.scheduling.view.containerinstance;

import de.daxu.swamp.common.jpa.Identifiable;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ContainerInstanceView extends Identifiable {

    @Embedded
    @NotNull( message = "{NotNull.ContainerInstanceView.containerInstanceId}" )
    private ContainerInstanceId containerInstanceId;

    @NotNull( message = "{NotNull.ContainerInstanceView.containerInstanceId}" )
    @Column( name = "name" )
    private String name;

    private ContainerInstanceView() {}

    private ContainerInstanceView( ContainerInstanceId containerInstanceId, String name ) {
        this.containerInstanceId = containerInstanceId;
        this.name = name;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }

    public String getName() {
        return name;
    }

    public static class ContainerInstanceViewBuilder {

        private ContainerInstanceId containerInstanceId;
        private String name;

        public static ContainerInstanceViewBuilder aContainerInstanceView() {
            return new ContainerInstanceViewBuilder();
        }

        public ContainerInstanceViewBuilder withContainerInstanceId( ContainerInstanceId containerInstanceId ) {
            this.containerInstanceId = containerInstanceId;
            return this;
        }

        public ContainerInstanceViewBuilder withName( String name ) {
            this.name = name;
            return this;
        }

        public ContainerInstanceView build() {
            return new ContainerInstanceView( containerInstanceId, name );
        }
    }

}
