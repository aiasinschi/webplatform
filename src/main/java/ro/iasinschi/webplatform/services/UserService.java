package ro.iasinschi.webplatform.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ro.iasinschi.webplatform.model.PlatformUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Map;

/**
 * File UserService.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/2/2015 6:59 PM.
 */
public class UserService {

    private static final Log LOG = LogFactory.getLog(UserService.class);

    private static EntityManager manager;

    private static EntityTransaction transaction;

    public static PlatformUser getUserForUsername(String username){
        if (!getTransaction().isActive()) {
            getTransaction().begin();
        }
        PlatformUser user = null;
        try {
            user = (PlatformUser) getManager().createNativeQuery(
                    String.format("Select * From PlatformUser Where username='%s';", username), PlatformUser.class).getSingleResult();
        } catch (Exception ex) {
            LOG.debug("Could not found user having username = " + username + "\n Exception: " + ex);
        }
        getTransaction().commit();
        return user;
    }

    public static long getNextAvailableId() {
        if (!getTransaction().isActive()) {
            getTransaction().begin();
        }
        Long maxIdUser = 0L;
        try {
            maxIdUser = (Long) getManager().createNativeQuery("Select max(id) as maxid From PlatformUser;").getSingleResult();
        } catch (Exception ex) {
            LOG.debug("No records could be found. Exception: " + ex.toString());
        }
        System.out.println("maxIdUser = " + maxIdUser);
        getTransaction().commit();
        return maxIdUser != null ? maxIdUser + 1 : 1;
    }

    public static void addUser(PlatformUser user){
        if (!getTransaction().isActive()) {
            getTransaction().begin();
        }
        getManager().persist(user);
        getTransaction().commit();
    }


    public static EntityManager getManager() {
        if (manager == null){
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
            manager = factory.createEntityManager();
            transaction = getManager().getTransaction();
        }
        return manager;
    }

    public static EntityTransaction getTransaction() {
        if (manager == null){
            getManager();
        }
        return transaction;
    }
}
