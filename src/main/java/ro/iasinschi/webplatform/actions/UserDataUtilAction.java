package ro.iasinschi.webplatform.actions;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import ro.iasinschi.webplatform.model.UserData;
import ro.iasinschi.webplatform.services.UserDataService;

import java.util.ArrayList;
import java.util.List;

/**
 * File UserDataUtilAction.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 2/12/2015 9:26 PM.
 */
public class UserDataUtilAction extends ActionSupport implements Action {

    private Long userId;

    private String jsonResult;

    public String getTablesForUser(){
        if (userId == null){
            return Action.LOGIN;
        }
        List<UserData> data = UserDataService.getUserDataForUserID(userId);
        List<String> tables = new ArrayList<>();
        for (UserData ud: data){
            tables.add(ud.getTablename());
        }
        jsonResult = new Gson().toJson(tables);
        return Action.SUCCESS;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }
}
