package ro.iasinschi.webplatform.util;

import ro.iasinschi.webplatform.model.PlatformUser;

/**
 * File ApplicationContext.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/10/2015 5:43 PM.
 */
public class ApplicationContext {

    private static Object[][] data;

    private static PlatformUser loggedUser;

    public ApplicationContext(){

    }

    public static Object[][] getData() {
        return data;
    }

    public static void setData(Object[][] data) {
        ApplicationContext.data = data;
    }

    public static PlatformUser getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(PlatformUser loggedUser) {
        ApplicationContext.loggedUser = loggedUser;
    }
}
