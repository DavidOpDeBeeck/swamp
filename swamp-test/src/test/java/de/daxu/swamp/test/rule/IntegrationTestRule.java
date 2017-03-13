package de.daxu.swamp.test.rule;

import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

public class IntegrationTestRule implements TestRule {

    private final FlywayRule flyway;
    private final HibernateRule database;

    public IntegrationTestRule(SpringRule spring) {
        flyway = new FlywayRule(spring.getInstance(DataSource.class));
        database = new HibernateRule(spring.getInstance(EntityManagerFactory.class));
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return RuleChain
                .outerRule(flyway)
                .around(database)
                .apply(base, description);
    }

    public void save(Object... o) {
        database.save(o);
    }

    public <T> T find(String id, Class<T> returnType) {
        return database.find(id, returnType);
    }
}
