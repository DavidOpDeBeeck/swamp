package de.daxu.swamp.core;

import de.daxu.swamp.common.time.Dates;
import de.daxu.swamp.core.configuration.RunConfiguration;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.core.location.Location;

import java.time.LocalDateTime;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.core.ProjectTestConstants.Containers.Aliases.ALIAS;
import static de.daxu.swamp.core.ProjectTestConstants.Containers.Aliases.ANOTHER_ALIAS;
import static de.daxu.swamp.core.configuration.ImageConfigurationTestBuilder.anImageConfiguration;
import static de.daxu.swamp.core.container.EnvironmentVariableTestBuilder.anEnvironmentVariable;
import static de.daxu.swamp.core.container.PortMappingTestBuilder.aPortMapping;
import static de.daxu.swamp.core.continent.Continent.Builder.aContinent;

public class ProjectTestConstants {

    public static class Project {

        public final static String PROJECT_NAME = "projectName";
        public final static String ANOTHER_PROJECT_NAME = "anotherProjectName";
        public final static String PROJECT_DESCRIPTION = "projectDescription";
        public final static String ANOTHER_PROJECT_DESCRIPTION = "anotherProjectDescription";
        public final static LocalDateTime PROJECT_CREATED_AT = Dates.now();
        public final static LocalDateTime ANOTHER_PROJECT_CREATED_AT = Dates.now();
        public final static Set<Container> PROJECT_CONTAINERS = newHashSet();
        public final static Set<Container> ANOTHER_PROJECT_CONTAINERS = newHashSet();

    }

    public static class Containers {

        public final static Set<String> CONTAINER_ALIASES = newHashSet(ALIAS);
        public final static Set<String> ANOTHER_CONTAINER_ALIASES = newHashSet(ANOTHER_ALIAS);
        public final static RunConfiguration CONTAINER_RUN_CONFIGURATION = anImageConfiguration().build();
        public final static Set<Location> CONTAINER_POTENTIAL_LOCATIONS = newHashSet(aContinent().build());
        public final static Set<PortMapping> CONTAINER_PORT_MAPPINGS = newHashSet(aPortMapping().build());
        public final static Set<EnvironmentVariable> CONTAINER_ENVIRONMENT_VARIABLES = newHashSet(anEnvironmentVariable().build());

        public static class Aliases {

            public final static String ALIAS = "containerAlias";
            public final static String ANOTHER_ALIAS = "anotherContainerAlias";

        }

        public static class PortMappings {

            public final static Integer INTERNAL_PORT = 8080;
            public final static Integer ANOTHER_INTERNAL_PORT = 8888;
            public final static Integer EXTERNAL_PORT = 8080;
            public final static Integer ANOTHER_EXTERNAL_PORT = 8888;

        }

        public static class EnvironmentVariables {

            public final static String ENVIRONMENT_VARIABLE_NAME = "environmentVariableName";
            public final static String ANOTHER_ENVIRONMENT_VARIABLE_NAME = "anotherEnvironmentVariableName";
            public final static String ENVIRONMENT_VARIABLE_VALUE = "environmentVariableValue";
            public final static String ANOTHER_ENVIRONMENT_VARIABLE_VALUE = "anotherEnvironmentVariableValue";

        }

        public static class RunConfigurations {

            public final static String IMAGE_NAME = "imageName";
            public final static String ANOTHER_IMAGE_NAME = "imageName";

            public final static String DOCKER_FILE = "dockerFile";
            public final static String ANOTHER_DOCKER_FILE = "anotherDockerFile";

            public final static String GIT_URL = "gitUrl";
            public final static String ANOTHER_GIT_URL = "anotherGitUrl";
            public final static String GIT_BRANCH = "gitBranch";
            public final static String ANOTHER_GIT_BRANCH = "anotherGitBranch";
            public final static String GIT_PATH = "gitPath";
            public final static String ANOTHER_GIT_PATH = "anotherGitPath";

        }

    }

}
