package de.daxu.swamp.test.rule;

import org.flywaydb.core.Flyway;
import org.junit.rules.ExternalResource;

import javax.sql.DataSource;

public class FlywayRule extends ExternalResource {

    private final Flyway flyway;

    FlywayRule(DataSource dataSource) {
        flyway = setup(dataSource);
    }

    private Flyway setup(DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("classpath:migration");
        flyway.setBaselineOnMigrate(false);
        return flyway;
    }

    @Override
    protected void before() {
        flyway.migrate();
    }

    @Override
    protected void after() {
        flyway.clean();
    }
}
