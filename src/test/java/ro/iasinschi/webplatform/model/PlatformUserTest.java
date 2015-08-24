package ro.iasinschi.webplatform.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;
import ro.iasinschi.webplatform.services.UserService;
import ro.iasinschi.webplatform.util.PasswordHasher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * File PlatformUserTest.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/2/2015 5:32 PM.
 */
@Ignore
public class PlatformUserTest {

    private EntityManager manager;

    private PlatformUser testuser;

    private Log LOG = LogFactory.getLog(PlatformUserTest.class);

    private String testusername = "testuser";

    private String testpassword = "revenge";


    @Before
    public void setUp(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        testuser = new PlatformUser(testusername, "Test User", "aiasinschi@gmail.com", PasswordHasher.createHash(testpassword));
        manager.persist(testuser);
        transaction.commit();
    }

    @Test
    public void testCreateUser(){
        List<PlatformUser> resultList = manager.createNativeQuery("Select * From PlatformUser;", PlatformUser.class).getResultList();
        Assert.assertNotNull("Result list should not be null!! ", resultList);
        Assert.assertTrue("Result list should have one entry!!", resultList.size() > 0);
    }

    @Test
    public void testGetUserById(){
        List<PlatformUser> result = manager.createNativeQuery("Select * From PlatformUser Where id=1;", PlatformUser.class).getResultList();
        Assert.assertNotNull("Result list should not be null!! ", result);
        Assert.assertEquals("We should get exactly one result!!", 1, result.size());
        Assert.assertTrue("Username should be aiasinschi!!", result.get(0).getUsername().equals(testusername));
    }

    @Test
    public void testGetMaxId(){
        long maxid = UserService.getNextAvailableId();
        System.out.println("Obtained maxid=" + maxid);
        Assert.assertTrue("Maximum id should be > 1", maxid > 1);
    }

    @After
    public void cleanUp(){
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.remove(testuser);
        transaction.commit();
    }
}
