package ro.iasinschi.webplatform.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * File LogoutAction.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/2/2015 4:52 PM.
 */
public class LogoutAction extends ActionSupport implements Action, SessionAware {

    Map<String, Object> session;

    private String logout;

    @Override
    public String execute(){
        if (session != null){
            if (session.get("username") != null){
                session.put("username", null);
            }
        }
        return Action.SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        session = map;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }
}