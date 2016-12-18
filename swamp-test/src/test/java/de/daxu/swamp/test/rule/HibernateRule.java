package de.daxu.swamp.test.rule;

import org.junit.rules.ExternalResource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;

public class HibernateRule extends ExternalResource {

    private EntityManager entityManager;
    private final EntityManagerFactory factory;

    public HibernateRule( EntityManagerFactory entityManagerFactory ) {
        this.factory = entityManagerFactory;
    }

    @Override
    protected void before() throws Exception {
        entityManager = factory.createEntityManager();
    }

    @Override
    protected void after() {
        entityManager.close();
    }

    public void persist( Object... o ) {
        entityManager.getTransaction().begin();
        Arrays.stream( o )
                .forEach( entityManager::persist );
        entityManager.getTransaction().commit();
    }

    public <T> T find( String id, Class<T> returnType ) {
        entityManager.clear();
        return entityManager.find( returnType, id );
    }
}
