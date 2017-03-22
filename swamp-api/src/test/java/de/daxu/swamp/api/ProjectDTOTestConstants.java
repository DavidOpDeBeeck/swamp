package de.daxu.swamp.api;

import de.daxu.swamp.api.configuration.dto.ImageConfigurationDTO;
import de.daxu.swamp.api.containertemplate.dto.ContainerTemplateCreateDTO;
import de.daxu.swamp.api.containertemplate.dto.EnvironmentVariableDTO;
import de.daxu.swamp.api.containertemplate.dto.PortMappingDTO;
import de.daxu.swamp.api.project.dto.ProjectCreateDTO;
import de.daxu.swamp.api.version.dto.VersionDTO;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.api.ProjectDTOTestConstants.EnvironmentVariableDTOs.anEnvironmentVariableDTO;
import static de.daxu.swamp.api.ProjectDTOTestConstants.EnvironmentVariableDTOs.anotherEnvironmentVariableDTO;
import static de.daxu.swamp.api.ProjectDTOTestConstants.ImageConfigurationDTOs.anImageConfigurationDTO;
import static de.daxu.swamp.api.ProjectDTOTestConstants.ImageConfigurationDTOs.anotherImageConfigurationDTO;
import static de.daxu.swamp.api.ProjectDTOTestConstants.PortMappingDTOs.aPortMappingDTO;
import static de.daxu.swamp.api.ProjectDTOTestConstants.PortMappingDTOs.anotherPortMappingDTO;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.Aliases.ANOTHER_ALIAS;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.Aliases.AN_ALIAS;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.EnvironmentVariables.*;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.PortMappings.*;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.RunConfigurations.ANOTHER_IMAGE_NAME;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.RunConfigurations.AN_IMAGE_NAME;
import static de.daxu.swamp.core.ProjectTestConstants.Projects.*;

public class ProjectDTOTestConstants {

    public static class VersionDTOs {

        public final static String VERSION = "test";

        public static VersionDTO aVersionDTO() {
            return new VersionDTO(VERSION);
        }

    }

    public static class ProjectCreateDTOs {

        public static ProjectCreateDTO aProjectCreateDTO() {
            return new ProjectCreateDTO.Builder()
                    .withName(A_PROJECT_NAME)
                    .withDescription(A_PROJECT_DESCRIPTION)
                    .build();
        }

        public static ProjectCreateDTO anotherProjectCreateDTO() {
            return new ProjectCreateDTO.Builder()
                    .withName(ANOTHER_PROJECT_NAME)
                    .withDescription(ANOTHER_PROJECT_DESCRIPTION)
                    .build();
        }

    }

    public static class ContainerTemplateCreateDTOs {

        public static ContainerTemplateCreateDTO aContainerTemplateCreateDTO() {
            return new ContainerTemplateCreateDTO.Builder()
                    .withAliases(newHashSet(AN_ALIAS))
                    .withEnvironmentVariables(newHashSet(anEnvironmentVariableDTO()))
                    .withPortMappings(newHashSet(aPortMappingDTO()))
                    .withPotentialLocations(newHashSet())
                    .withRunConfiguration(anImageConfigurationDTO())
                    .build();
        }

        public static ContainerTemplateCreateDTO anotherContainerCreateDTO() {
            return new ContainerTemplateCreateDTO.Builder()
                    .withAliases(newHashSet(ANOTHER_ALIAS))
                    .withEnvironmentVariables(newHashSet(anotherEnvironmentVariableDTO()))
                    .withPortMappings(newHashSet(anotherPortMappingDTO()))
                    .withPotentialLocations(newHashSet())
                    .withRunConfiguration(anotherImageConfigurationDTO())
                    .build();
        }

    }

    public static class EnvironmentVariableDTOs {

        public static EnvironmentVariableDTO anEnvironmentVariableDTO() {
            return new EnvironmentVariableDTO.Builder()
                    .withName(AN_ENVIRONMENT_VARIABLE_NAME)
                    .withValue(AN_ENVIRONMENT_VARIABLE_VALUE)
                    .build();
        }

        public static EnvironmentVariableDTO anotherEnvironmentVariableDTO() {
            return new EnvironmentVariableDTO.Builder()
                    .withName(ANOTHER_ENVIRONMENT_VARIABLE_NAME)
                    .withValue(ANOTHER_ENVIRONMENT_VARIABLE_VALUE)
                    .build();
        }

    }

    public static class PortMappingDTOs {

        public static PortMappingDTO aPortMappingDTO() {
            return new PortMappingDTO.Builder()
                    .withInternal(AN_INTERNAL_PORT)
                    .withExternal(AN_EXTERNAL_PORT)
                    .build();
        }

        public static PortMappingDTO anotherPortMappingDTO() {
            return new PortMappingDTO.Builder()
                    .withInternal(ANOTHER_INTERNAL_PORT)
                    .withExternal(ANOTHER_EXTERNAL_PORT)
                    .build();
        }

    }

    public static class ImageConfigurationDTOs {

        public static ImageConfigurationDTO anImageConfigurationDTO() {
            return new ImageConfigurationDTO.Builder()
                    .withName(AN_IMAGE_NAME)
                    .build();
        }

        public static ImageConfigurationDTO anotherImageConfigurationDTO() {
            return new ImageConfigurationDTO.Builder()
                    .withName(ANOTHER_IMAGE_NAME)
                    .build();
        }

    }

}
