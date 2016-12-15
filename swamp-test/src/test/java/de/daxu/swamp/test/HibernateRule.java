package de.daxu.swamp.test;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateRule extends ExternalResource {

    private Logger logger = LoggerFactory.getLogger( HibernateRule.class );
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

    public void persist( Object o ) {
        logger.info( "BEGIN TRANSACTION" );
        entityManager.getTransaction().begin();
        entityManager.persist( o );
        entityManager.getTransaction().commit();
        logger.info( "COMMIT TRANSACTION" );
    }

    public EntityManager entityManager() {
        return entityManager;
    }
}