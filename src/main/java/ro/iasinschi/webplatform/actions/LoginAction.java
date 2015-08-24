package ro.iasinschi.webplatform.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import ro.iasinschi.webplatform.model.PlatformUser;
import ro.iasinschi.webplatform.services.UserService;
import ro.iasinschi.webplatform.util.ApplicationContext;
import ro.iasinschi.webplatform.util.PasswordHasher;

import java.util.Map;

/**
 * File LoginAction.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/2/2015 9:11 AM.
 */
public class LoginAction extends ActionSupport implements Action, SessionAware, RequestAware{

    Map<String, Object> session;

    Map<String, Object> request;

    private String username;

    private String password;

    private String login;

    @Override
    public String execute() throws Exception {
        String loginError = "";
        System.out.println("Username: " + username + " Password: " + password);
        PlatformUser user = UserService.getUserForUsername(username);
        if (user == null){
            loginError = "User does not exist!";
            LOG.debug(loginError);
            session.put("loginError", loginError);
            return ERROR;
        }
        if (PasswordHasher.validatePassword(password, user.getPassword())) {
            session.put("fullname", user.getCompleteName());
            session.put("userid", user.getId());
            session.put("loginError", "");
            LOG.debug("Succesfull login as " + user.getCompleteName());
            ApplicationContext.setLoggedUser(user);
            return Action.SUCCESS;
        } else{
            loginError = "Password is incorrect!";
            session.put("loginError", loginError);
            LOG.debug(loginError);
            return ERROR;
        }
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    @Override
    public void setRequest(Map<String, Object> map) {
        this.request = map;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public Map<String, Object> getRequest() {
        return request;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
