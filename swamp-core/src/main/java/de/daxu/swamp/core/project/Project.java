package de.daxu.swamp.core.project;

import de.daxu.swamp.common.Identifiable;
import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Entity
@Table(name = "project")
@SuppressWarnings("unused")
public class Project extends Identifiable {

    @NotBlank(message = "{NotBlank.Project.name}")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "{NotBlank.Project.description}")
    @Lob
    @Column(name = "description")
    private String description;

    @NotNull(message = "{NotNull.Project.createdAt}")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Set<ContainerTemplate> containerTemplates;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Project() {
    }

    private Project(String name, String description, LocalDateTime createdAt, Set<ContainerTemplate> containerTemplates) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.containerTemplates = containerTemplates;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<ContainerTemplate> getContainerTemplates() {
        return containerTemplates;
    }

    public boolean addContainerTemplate(ContainerTemplate containerTemplate) {
        if (this.containerTemplates == null) this.containerTemplates = newHashSet();
        return this.containerTemplates.add(containerTemplate);
    }

    public boolean removeContainerTemplate(ContainerTemplate containerTemplate) {
        return this.containerTemplates.remove(containerTemplate);
    }

    public static class Builder {

        private String name;
        private String description;
        private LocalDateTime createdAt;
        private Set<ContainerTemplate> containerTemplates;

        public static Builder aProject() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withContainers(Set<ContainerTemplate> containerTemplates) {
            this.containerTemplates = containerTemplates;
            return this;
        }

        public Project build() {
            return new Project(name, description, createdAt, containerTemplates);
        }
    }
}
