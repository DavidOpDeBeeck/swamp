package de.daxu.swamp.test.rule;

import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

public class IntegrationTestRule implements TestRule {

    private final HibernateRule database;
    private final FlywayRule flyway;

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

    public void persist( Object... o ) {
        database.persist( o );
    }

    public <T> T find( String id, Class<T> returnType ) {
        return database.find( id, returnType );
    }
}
