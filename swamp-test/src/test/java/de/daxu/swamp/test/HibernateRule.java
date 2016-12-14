package de.daxu.swamp.test;

import org.junit.rules.ExternalResource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateRule extends ExternalResource {

    private SpringRule spring;
    private EntityManager entityManager;

    public HibernateRule( SpringRule spring ) {
        this.spring = spring;
    }

    @Override
    protected void before() throws Exception {
        EntityManagerFactory factory = createManager();
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @Override
    protected void after() {
        entityManager.getTransaction().commit();
        entityManager.getTransaction().rollback();
        entityManager.close();
    }

    private EntityManagerFactory createManager() {
        return spring.context().getBean( EntityManagerFactory.class );
    }

    public EntityManager entityManager() {
        return entityManager;
    }
}
