package de.daxu.swamp.test;

import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

public class IntegrationTestRule implements TestRule {

    private HibernateRule database;
    private FlywayRule flyway;

    public IntegrationTestRule( SpringRule spring ) {
        database = new HibernateRule( spring.getInstance( EntityManagerFactory.class ) );
        flyway = new FlywayRule( spring.getInstance( DataSource.class ) );
    }

    @Override
    public Statement apply( Statement base, Description description ) {
        return RuleChain
                .outerRule( database )
                .around( flyway )
                .apply( base, description );
    }

    public EntityManager entityManager() {
        return database.entityManager();
    }

    public void persist( Object o ) {
        database.persist( o );
    }
}
