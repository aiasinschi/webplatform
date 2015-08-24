package ro.iasinschi.webplatform.util;


import ro.iasinschi.webplatform.model.PlatformUser;
import ro.iasinschi.webplatform.services.UserService;
import ro.iasinschi.webplatform.util.constants.ApplicationConstants;
import ro.iasinschi.webplatform.util.exceptions.LoginException;
import ro.iasinschi.webplatform.util.exceptions.RegistrationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File AuthenticationUtil.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/10/2015 10:26 AM.
 */
public class AuthenticationUtil {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Method used to check the login user credentials
     * @param username the username
     * @param password the password
     * @return true if authentication succeeded, false otherwise
     * @throws ro.iasinschi.webplatform.util.exceptions.LoginException
     * @throws javax.security.auth.login.AccountLockedException
     */
    public static boolean authenticate(String username, String password) throws LoginException {
        PlatformUser user = UserService.getUserForUsername(username);
        if (user == null){
            throw new LoginException(ApplicationConstants.NON_EXISTENT_USERNAME_EX);
        }
        if (PasswordHasher.validatePassword(password, user.getPassword())) {
            ApplicationContext.setLoggedUser(user);
            return true;
        } else {
            throw new LoginException(ApplicationConstants.INCORRECT_PASSWORD_EX);
        }
    }

    /**
     * Method used to register a new user
     * @param username the username
     * @param password the password
     * @param repeatpassword the repeat password
     * @param email the e-mail
     * @param fullname the full name
     * @return
     */
    public static boolean register(String username, String password, String repeatpassword, String email, String fullname) throws RegistrationException {
        PlatformUser user = UserService.getUserForUsername(username);
        if (user != null){
            throw new RegistrationException(ApplicationConstants.USER_EXISTS_EX);
        }
        if (! password.equals(repeatpassword)){
            throw new RegistrationException(ApplicationConstants.PASSWORDS_DONT_MATCH_EX);
        }
        if (! validEmail(email)){
            throw new RegistrationException(ApplicationConstants.INVALID_EMAIL_EX);
        }
        user = new PlatformUser(username, fullname, email, PasswordHasher.createHash(password));
        UserService.addUser(user);
        return true;
    }

    private static boolean validEmail(final String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
