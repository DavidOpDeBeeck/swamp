package de.daxu.swamp.test.integration;

import de.daxu.swamp.test.SwampTestApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith( SpringRunner.class )
@Transactional
@AutoConfigureTestEntityManager
@ContextConfiguration( classes = SwampTestApplication.class )
@TestPropertySource( locations = "classpath:test.properties" )
public abstract class ServiceIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    public TestEntityManager entityManager() {
        return entityManager;
    }
}
