package de.daxu.swamp.api.server.converter;

import de.daxu.swamp.core.server.Server;
import org.junit.Test;

import static de.daxu.swamp.api.LocationDTOTestConstants.ServerDTOs.aServerCreateDTO;
import static de.daxu.swamp.core.LocationTestConstants.Servers.aServer;
import static org.assertj.core.api.Assertions.assertThat;

public class ServerCreateConverterTest {

    @Test
    public void toDomain() throws Exception {
        ServerCreateConverter converter = new ServerCreateConverter();

        Server server = converter.toDomain(aServerCreateDTO());

        assertThat(server)
                .isEqualTo(aServer());
    }

}