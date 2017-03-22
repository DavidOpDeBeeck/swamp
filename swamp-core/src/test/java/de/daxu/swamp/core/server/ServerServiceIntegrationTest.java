package de.daxu.swamp.core.server;

import de.daxu.swamp.test.rule.IntegrationTestRule;
import de.daxu.swamp.test.rule.SpringRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static de.daxu.swamp.common.comparator.ReflectionComparator.byReflection;
import static de.daxu.swamp.core.LocationTestConstants.Servers.*;
import static de.daxu.swamp.test.rule.SpringRule.spring;
import static org.assertj.core.api.Assertions.assertThat;

public class ServerServiceIntegrationTest {

    @ClassRule
    public static SpringRule spring = spring();
    @Rule
    public IntegrationTestRule integration = new IntegrationTestRule(spring);

    private ServerService serverService
            = spring.getInstance(ServerService.class);

    @Test
    public void getServer() {
        Server server = aServer();
        integration.save(server);

        Server actual = serverService.getServer(server.getId());

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(server);
    }

    @Test
    public void getServerByName() {
        Server aServer = aServer();
        integration.save(aServer);

        Server actual = serverService.getServerByName(A_SERVER_NAME);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aServer);
    }

    @Test
    public void updateServer() {
        Server aServer = aServer();
        integration.save(aServer);

        aServer.setName(ANOTHER_SERVER_NAME);
        aServer.setIp(ANOTHER_SERVER_IP);
        aServer.setKey(ANOTHER_SERVER_KEY);
        aServer.setCertificate(ANOTHER_SERVER_CERTIFICATE);
        aServer.setCaCertificate(ANOTHER_SERVER_CA_CERTIFICATE);

        Server actual = serverService.updateServer(aServer);

        assertThat(actual)
                .usingComparator(byReflection())
                .isEqualTo(aServerBuilder()
                        .withName(ANOTHER_SERVER_NAME)
                        .withIp(ANOTHER_SERVER_IP)
                        .withKey(ANOTHER_SERVER_KEY)
                        .withCertificate(ANOTHER_SERVER_CERTIFICATE)
                        .withCaCertificate(ANOTHER_SERVER_CA_CERTIFICATE)
                        .build());
    }

    @Test
    public void getAllServers() {
    }
}