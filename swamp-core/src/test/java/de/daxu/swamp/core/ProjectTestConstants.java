package de.daxu.swamp.core;

import de.daxu.swamp.common.time.Dates;
import de.daxu.swamp.core.configuration.DockerfileConfiguration;
import de.daxu.swamp.core.configuration.GitConfiguration;
import de.daxu.swamp.core.configuration.ImageConfiguration;
import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.core.containertemplate.EnvironmentVariable;
import de.daxu.swamp.core.containertemplate.PortMapping;
import de.daxu.swamp.core.project.Project;

import java.time.LocalDateTime;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.core.CredentialsTestConstants.UsernamePassword.anUsernamePasswordCredentials;
import static de.daxu.swamp.core.CredentialsTestConstants.UsernamePassword.anotherUsernamePasswordCredentials;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.Aliases.ANOTHER_ALIAS;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.Aliases.AN_ALIAS;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.EnvironmentVariables.anEnvironmentVariable;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.EnvironmentVariables.anotherEnvironmentVariable;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.PortMappings.aPortMapping;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.PortMappings.anotherPortMapping;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.RunConfigurations.anImageConfiguration;
import static de.daxu.swamp.core.ProjectTestConstants.ContainerTemplates.RunConfigurations.anotherImageConfiguration;

public class ProjectTestConstants {

    public static class Projects {

        public final static String A_PROJECT_NAME = "projectName";
        public final static String ANOTHER_PROJECT_NAME = "anotherProjectName";
        public final static String A_PROJECT_DESCRIPTION = "projectDescription";
        public final static String ANOTHER_PROJECT_DESCRIPTION = "anotherProjectDescription";
        public final static LocalDateTime PROJECT_CREATED_AT = Dates.now();
        public final static LocalDateTime ANOTHER_PROJECT_CREATED_AT = Dates.now();

        public static Project aProject() {
            return aProjectBuilder().build();
        }

        public static Project aProject(ContainerTemplate containerTemplate) {
            return aProjectBuilder()
                    .withContainers(newHashSet(containerTemplate))
                    .build();
        }

        public static Project anotherProject() {
            return aProjectBuilder()
                    .withName(ANOTHER_PROJECT_NAME)
                    .withDescription(ANOTHER_PROJECT_DESCRIPTION)
                    .withCreatedAt(ANOTHER_PROJECT_CREATED_AT)
                    .build();
        }

        public static Project.Builder aProjectBuilder() {
            return new Project.Builder()
                    .withName(A_PROJECT_NAME)
                    .withDescription(A_PROJECT_DESCRIPTION)
                    .withCreatedAt(PROJECT_CREATED_AT)
                    .withContainers(newHashSet());
        }

    }

    public static class ContainerTemplates {

        public static ContainerTemplate aContainerTemplate() {
            return aContainerTemplateBuilder().build();
        }

        public static ContainerTemplate.Builder aContainerTemplateBuilder() {
            return new ContainerTemplate.Builder()
                    .withAliases(newHashSet(AN_ALIAS))
                    .withRunConfiguration(anImageConfiguration())
                    .withPortMappings(newHashSet(aPortMapping()))
                    .withPotentialLocations(newHashSet())
                    .withEnvironmentVariables(newHashSet(anEnvironmentVariable()));
        }

        public static ContainerTemplate anotherContainerTemplate() {
            return new ContainerTemplate.Builder()
                    .withAliases(newHashSet(ANOTHER_ALIAS))
                    .withRunConfiguration(anotherImageConfiguration())
                    .withPortMappings(newHashSet(anotherPortMapping()))
                    .withPotentialLocations(newHashSet())
                    .withEnvironmentVariables(newHashSet(anotherEnvironmentVariable()))
                    .build();
        }

        public static class Aliases {

            public final static String AN_ALIAS = "containerAlias";
            public final static String ANOTHER_ALIAS = "anotherContainerAlias";

        }

        public static class PortMappings {

            public final static Integer AN_INTERNAL_PORT = 8080;
            public final static Integer ANOTHER_INTERNAL_PORT = 8888;
            public final static Integer AN_EXTERNAL_PORT = 8080;
            public final static Integer ANOTHER_EXTERNAL_PORT = 8888;

            public static PortMapping aPortMapping() {
                return new PortMapping.Builder()
                        .withInternal(AN_INTERNAL_PORT)
                        .withExternal(AN_EXTERNAL_PORT)
                        .build();
            }

            public static PortMapping anotherPortMapping() {
                return new PortMapping.Builder()
                        .withInternal(ANOTHER_INTERNAL_PORT)
                        .withExternal(ANOTHER_EXTERNAL_PORT)
                        .build();
            }

        }

        public static class EnvironmentVariables {

            public final static String AN_ENVIRONMENT_VARIABLE_NAME = "environmentVariableName";
            public final static String ANOTHER_ENVIRONMENT_VARIABLE_NAME = "anotherEnvironmentVariableName";
            public final static String AN_ENVIRONMENT_VARIABLE_VALUE = "environmentVariableValue";
            public final static String ANOTHER_ENVIRONMENT_VARIABLE_VALUE = "anotherEnvironmentVariableValue";

            public static EnvironmentVariable anEnvironmentVariable() {
                return new EnvironmentVariable.Builder()
                        .withName(AN_ENVIRONMENT_VARIABLE_NAME)
                        .withValue(AN_ENVIRONMENT_VARIABLE_VALUE)
                        .build();
            }

            public static EnvironmentVariable anotherEnvironmentVariable() {
                return new EnvironmentVariable.Builder()
                        .withName(ANOTHER_ENVIRONMENT_VARIABLE_NAME)
                        .withValue(ANOTHER_ENVIRONMENT_VARIABLE_VALUE)
                        .build();
            }

        }

        public static class RunConfigurations {

            public final static String AN_IMAGE_NAME = "imageName";
            public final static String ANOTHER_IMAGE_NAME = "anotherImageName";

            public static ImageConfiguration anImageConfiguration() {
                return new ImageConfiguration.Builder()
                        .withName(AN_IMAGE_NAME)
                        .build();
            }

            public static ImageConfiguration anotherImageConfiguration() {
                return new ImageConfiguration.Builder()
                        .withName(ANOTHER_IMAGE_NAME)
                        .build();
            }

            public final static String DOCKER_FILE = "dockerFile";
            public final static String ANOTHER_DOCKER_FILE = "anotherDockerFile";

            public static DockerfileConfiguration aDockerfileConfiguration() {
                return new DockerfileConfiguration.Builder()
                        .withDockerfile(DOCKER_FILE)
                        .build();
            }

            public static DockerfileConfiguration anotherDockerfileConfiguration() {
                return new DockerfileConfiguration.Builder()
                        .withDockerfile(ANOTHER_DOCKER_FILE)
                        .build();
            }

            public final static String GIT_URL = "gitUrl";
            public final static String ANOTHER_GIT_URL = "anotherGitUrl";
            public final static String GIT_BRANCH = "gitBranch";
            public final static String ANOTHER_GIT_BRANCH = "anotherGitBranch";
            public final static String GIT_PATH = "gitPath";
            public final static String ANOTHER_GIT_PATH = "anotherGitPath";

            public static GitConfiguration aGitConfiguration() {
                return new GitConfiguration.Builder()
                        .withUrl(GIT_URL)
                        .withBranch(GIT_BRANCH)
                        .withPath(GIT_PATH)
                        .withCredentials(anUsernamePasswordCredentials())
                        .build();
            }

            public static GitConfiguration anotherGitConfiguration() {
                return new GitConfiguration.Builder()
                        .withUrl(ANOTHER_GIT_URL)
                        .withBranch(ANOTHER_GIT_BRANCH)
                        .withPath(ANOTHER_GIT_PATH)
                        .withCredentials(anotherUsernamePasswordCredentials())
                        .build();
            }

        }
    }

}
