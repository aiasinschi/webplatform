package ro.iasinschi.webplatform.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import ro.iasinschi.webplatform.util.ApplicationContext;

/**
 * File HomeAction.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/31/2015 9:54 AM.
 */
public class HomeAction extends ActionSupport implements Action {

    @Override
    public String execute(){
        if (ApplicationContext.getLoggedUser() == null){
            return Action.LOGIN;
        } else {
            return Action.SUCCESS;
        }
    }
}
