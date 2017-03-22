package de.daxu.swamp.api.configuration.dto;

import de.daxu.swamp.core.configuration.RunConfigurationType;
import org.hibernate.validator.constraints.NotBlank;

public class DockerfileConfigurationDTO extends RunConfigurationDTO {

    @NotBlank(message = "{NotBlank.DockerfileConfigurationDTO.dockerfile}")
    private String dockerfile;

    @SuppressWarnings("unused")
    private DockerfileConfigurationDTO() {
        super(RunConfigurationType.DOCKERFILE);
    }

    private DockerfileConfigurationDTO(String dockerfile) {
        this();
        this.dockerfile = dockerfile;
    }

    public String getDockerfile() {
        return dockerfile;
    }

    public static class Builder {

        private String dockerfile;

        public Builder withDockerfile(String dockerfile) {
            this.dockerfile = dockerfile;
            return this;
        }

        public DockerfileConfigurationDTO build() {
            return new DockerfileConfigurationDTO(dockerfile);
        }
    }
}
