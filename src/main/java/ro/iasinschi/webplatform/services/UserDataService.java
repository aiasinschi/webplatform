package ro.iasinschi.webplatform.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ro.iasinschi.webplatform.model.UserData;

import javax.persistence.Query;
import java.util.List;

/**
 * File UserDataService.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/11/2015 11:42 AM.
 */
public class UserDataService extends SimpleService {

    private static final Log LOG = LogFactory.getLog(UserDataService.class);

    public static List<UserData> getUserDataForUserID(long userID){
        List<UserData> userDataList;
        if (!getTransaction().isActive()) {
            getTransaction().begin();
        }
        String sql = "SELECT * FROM userdata WHERE userid = ?";
        Query query = getManager().createNativeQuery(sql, UserData.class);
        query.setParameter(1, userID);
        // assume user has only one table
        userDataList = query.getResultList();
        getTransaction().commit();
        return userDataList;
    }

    public static int getTableCountForUserID(long userID){
        if (!getTransaction().isActive()) {
            getTransaction().begin();
        }
        String sql = "SELECT * FROM userdata WHERE userid = ?";
        Query query = getManager().createNativeQuery(sql, UserData.class);
        query.setParameter(1, userID);
        int result = query.getResultList().size();
        getTransaction().commit();
        return result;
    }

    public static void addUserData(UserData userData){
        if (!getTransaction().isActive()) {
            getTransaction().begin();
        }
        getManager().persist(userData);
        getTransaction().commit();
    }
}
