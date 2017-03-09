package de.daxu.swamp.docker.command;

import de.daxu.swamp.deploy.DeployResult;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;

public class DockerCommandExecutorTest {

    private static String WARNING_FORMAT = "Exception %s: %s";

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @InjectMocks
    private DockerCommandExecutor executor;

    @Test
    public void action_whenNoExceptionThrown_ResultSuccess() throws Exception {
        DeployResult<Void> result = executor.action(System.out::println);

        assertThat(result.success()).isTrue();
    }

    @Test
    public void action_whenExceptionThrown_ResultFailedAndContainsWarnings() throws Exception {
        DeployResult<Void> result = executor.action(behaviour -> {
            throw new RuntimeException("Exception");
        });

        assertThat(result.success()).isFalse();
        assertThat(result.warnings())
                .contains(String.format(WARNING_FORMAT, RuntimeException.class.getSimpleName(), "Exception"));
    }

    @Test
    public void result_whenNoExceptionThrown_ResultSuccessAndContainsResult() throws Exception {
        DeployResult<Boolean> result = executor.result(behaviour -> true);

        assertThat(result.success()).isTrue();
        assertThat(result.get()).isTrue();
    }

    @Test
    public void result_whenExceptionThrown_ResultFailedAndContainsWarnings() throws Exception {
        DeployResult<?> result = executor.result(behaviour -> {
            throw new RuntimeException("Exception");
        });

        assertThat(result.success()).isFalse();
        assertThat(result.warnings())
                .contains(String.format(WARNING_FORMAT, RuntimeException.class.getSimpleName(), "Exception"));
    }
}