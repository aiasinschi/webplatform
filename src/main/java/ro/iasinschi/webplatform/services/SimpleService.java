package ro.iasinschi.webplatform.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * File SimpleService.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/11/2015 11:44 AM.
 */
public class SimpleService {

    private static final Log LOG = LogFactory.getLog(SimpleService.class);

    private static EntityManager manager;

    private static EntityTransaction transaction;


    public static EntityManager getManager() {
        if (manager == null) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
            manager = factory.createEntityManager();
            transaction = getManager().getTransaction();
        }
        return manager;
    }

    public static EntityTransaction getTransaction() {
        if (manager == null) {
            getManager();
        }
        return transaction;
    }
}
