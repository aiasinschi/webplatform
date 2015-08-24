package ro.iasinschi.webplatform.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;
import ro.iasinschi.webplatform.model.PlatformUser;
import ro.iasinschi.webplatform.services.UserService;
import ro.iasinschi.webplatform.util.PasswordHasher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * File RegisterAction.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/2/2015 6:49 PM.
 */
public class RegisterAction extends ActionSupport implements Action, SessionAware {

    private String username;

    private String password;

    private String repeatpassword;

    private String fullname;

    private String email;

    private String register;

    Map<String, Object> session;

    private PlatformUser user;

    private Log LOG = LogFactory.getLog(RegisterAction.class);

    @Override
    public String execute(){
        List<String> errors = new ArrayList<>();
        if (password.trim().length() == 0){
            errors.add("Password length should be at least 1.");
        }
        if (!password.equals(repeatpassword)){
            errors.add("Password doesn't match the repeat password.");
        }
        if (username.trim().length() == 0){
            errors.add("Username is empty.");
        }
        if (fullname.trim().length() == 0){
            errors.add("User full name is empty.");
        }
        if (email.trim().length() == 0){
            errors.add("User e-mail is empty.");
        }
        user = UserService.getUserForUsername(username);
        if (user!=null){
            errors.add("User already exists. <br/>");
        }
        if (errors.size() > 0) {
            LOG.error(errors);
            session.put("registrationErrors", buildErrorMessage(errors));
            session.put("username", username);
            session.put("fullname", fullname);
            session.put("email", email);
            return Action.ERROR;
        } else {
            user = new PlatformUser(username, fullname, email, PasswordHasher.createHash(password));
            UserService.addUser(user);
            LOG.debug("Registered new user: \n" + user.toString());
            session.put("registrationErrors", "");
            return Action.SUCCESS;
        }
    }

    private String buildErrorMessage(List<String> errors){
        StringBuffer sb = new StringBuffer();
        for (String s: errors){
            sb.append(s + "<br/>");
        }
        return sb.toString();
    }


    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
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

    public String getRepeatpassword() {
        return repeatpassword;
    }

    public void setRepeatpassword(String repeatpassword) {
        this.repeatpassword = repeatpassword;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
}
