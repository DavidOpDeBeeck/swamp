package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.test.ResourceIntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectResourceTest extends ResourceIntegrationTest {

    @Test
    public void getAll() throws Exception {
        String body = restTemplate().getForObject( "/", String.class );
        assertThat( body ).contains( "error" );
    }

    @Test
    public void post() throws Exception {

    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void put() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}