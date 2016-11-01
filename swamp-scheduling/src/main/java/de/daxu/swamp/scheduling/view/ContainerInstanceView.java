package de.daxu.swamp.scheduling.view;

import de.daxu.swamp.common.Identifiable;

import javax.persistence.Entity;

@Entity
public class ContainerInstanceView extends Identifiable {

    private String name;

    private ContainerInstanceView() {
    }

    private ContainerInstanceView( String name ) {
        this.name = name;
    }

    public static class ContainerInstanceViewBuilder {

        private String name;

        public static ContainerInstanceViewBuilder aContainerInstanceView() {
            return new ContainerInstanceViewBuilder();
        }

        public ContainerInstanceViewBuilder withName( String name ) {
            this.name = name;
            return this;
        }

        public ContainerInstanceView build() {
            return new ContainerInstanceView(name);
        }
    }

}
