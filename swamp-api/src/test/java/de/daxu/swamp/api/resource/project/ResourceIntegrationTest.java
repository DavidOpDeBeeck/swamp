package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.api.SwampApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@AutoConfigureTestDatabase( connection = EmbeddedDatabaseConnection.H2 )
@SpringBootTest( classes = SwampApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@TestPropertySource( locations = "classpath:application.properties" )
public class ResourceIntegrationTest {

    @Autowired
    protected TestRestTemplate restTemplate;

}
