package de.daxu.swamp.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection.H2;
import static org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace.ANY;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@RunWith( SpringRunner.class )
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase( connection = H2, replace = ANY )
@SpringBootTest( classes = SwampTestApplication.class, webEnvironment = RANDOM_PORT )
@TestPropertySource( locations = "classpath:test.properties" )
public class ResourceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TestEntityManager entityManager;

    public TestRestTemplate restTemplate() {
        return restTemplate;
    }

    public TestEntityManager entityManager() {
        return entityManager;
    }
}
