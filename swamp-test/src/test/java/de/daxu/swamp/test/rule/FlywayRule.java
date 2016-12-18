package de.daxu.swamp.test.rule;

import org.flywaydb.core.Flyway;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class FlywayRule extends ExternalResource {

    private Logger logger = LoggerFactory.getLogger( FlywayRule.class );
    private final Flyway flyway;

    public FlywayRule( DataSource dataSource ) {
        flyway = setup( dataSource );
    }

    private Flyway setup( DataSource dataSource ) {
        Flyway flyway = new Flyway();
        flyway.setDataSource( dataSource );
        flyway.setLocations( "classpath:migration" );
        flyway.setBaselineOnMigrate( true );
        return flyway;
    }

    @Override
    protected void before() {
        logger.info( "----------------------" );
        logger.info( "    Flyway Migrate    " );
        logger.info( "----------------------" );
        flyway.migrate();
        logger.info( "----------------------" );
    }

    @Override
    protected void after() {
        logger.info( "----------------------" );
        logger.info( "     Flyway Clean     " );
        logger.info( "----------------------" );
        flyway.clean();
        logger.info( "----------------------" );
    }
}
