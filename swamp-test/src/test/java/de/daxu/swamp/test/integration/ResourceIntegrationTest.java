package de.daxu.swamp.test.integration;

import de.daxu.swamp.test.SwampTestApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith( SpringRunner.class )
@AutoConfigureTestEntityManager
@SpringBootTest( classes = SwampTestApplication.class, webEnvironment = RANDOM_PORT )
@TestPropertySource( locations = "classpath:test.properties" )
public abstract class ResourceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public TestRestTemplate restTemplate() {
        return restTemplate;
    }
}
