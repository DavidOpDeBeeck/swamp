package de.daxu.swamp.api.resource.project;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectResourceTest extends ResourceIntegrationTest {

    @Test
    public void getAll() throws Exception {
        String body = this.restTemplate.getForObject( "/", String.class );
        assertThat( body ).isEqualTo( "Hello World" );
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