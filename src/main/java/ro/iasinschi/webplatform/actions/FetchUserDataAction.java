package ro.iasinschi.webplatform.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ro.iasinschi.webplatform.model.PlatformUser;
import ro.iasinschi.webplatform.model.UserData;
import ro.iasinschi.webplatform.services.UserDataService;
import ro.iasinschi.webplatform.util.ApplicationContext;
import ro.iasinschi.webplatform.util.DatabaseUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * File FetchUserDataAction.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/31/2015 10:24 AM.
 */
public class FetchUserDataAction extends ActionSupport implements Action {

    private static Log LOG = LogFactory.getLog(FetchUserDataAction.class);

    private Long userId;

    private String tableName;

    private String jsonResult;

    @Override
    public String execute(){
        //TODO: WILL BE REMOVED AFTER SOLVING THE SHIT WITH JSON
        if (userId == null){ // it comes directly to request
            userId = 1L;
        }
        PlatformUser user = ApplicationContext.getLoggedUser();
        if (user == null){
            return null;
        }
        if (tableName == null){
            if (UserDataService.getTableCountForUserID(userId) == 0){
                LOG.debug("No tables found for user");
            } else {
                List<UserData> list = UserDataService.getUserDataForUserID(userId);
                tableName = list.get(0).getTablename();
                LOG.debug("Retrieved table name");
            }
        }
        List<String> columns = DatabaseUtil.getColumns(tableName);
        List<List<String>> data = DatabaseUtil.getDataFromTable(tableName, columns);
        jsonResult = buildCompleteJSON(columns, data);
        return Action.SUCCESS;
    }

    public static String buildCompleteJSON(List<String> columns, List<List<String>> data){
        StringBuilder json = new StringBuilder();
        json.append(String.format("{\"datastr\": %s, \"colModel\": %s, \"colNames\": %s}", buildJSONResult(columns, data), buildJSONModel(columns), buildJSONNames(columns)));
        return json.toString();
    }

    private static String buildJSONNames(List<String> columns){
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < columns.size() - 1; i ++){
            json.append(String.format("\"%s\", ", columns.get(i)));
        }
        json.append(String.format("\"%s\" ", columns.get(columns.size() - 1)));
        json.append("]");
        return json.toString();
    }


    private static String buildJSONResult(List<String> columns, List<List<String>> data){
        StringBuilder json = new StringBuilder();
        int records = data.size();
        json.append("\"rows\":[ ");
        for (int i = 0; i < data.size() - 1; i ++){
            json.append(buildJSONLine(columns, data.get(i)));
            json.append(", ");
        }
        json.append(buildJSONLine(columns, data.get(data.size() - 1)));
        json.append("]");
        return String.format("{\"total\": \"%s\", \"page\": \"%s\", \"records\": \"%s\", %s }", 10, 1, records, json.toString());
    }

    private static String buildJSONLine(List<String> columns, List<String> line){
        StringBuilder json = new StringBuilder();
        json.append(" {");
        for (int j = 0; j < columns.size() - 1; j ++){
            json.append(String.format("\"%s\": \"%s\",", columns.get(j), line.get(j)));
        }
        json.append(String.format("\"%s\": \"%s\"", columns.get(columns.size() - 1), line.get(columns.size() - 1)));
        json.append(" } ");
        return json.toString();
    }

    private static String buildJSONModel(List<String> columns){
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < columns.size() - 1; i++) {
            json.append(String.format("{\"name\": \"%s\", \"index\": \"%s\", \"width\": 100}, ", columns.get(i), columns.get(i)));
        }
        json.append(String.format("{\"name\": \"%s\", \"index\": \"%s\", \"width\": 100} ", columns.get(columns.size() - 1), columns.get(columns.size() - 1)));
        json.append(" ]");
        return json.toString();
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public static void main(String[] args) {
        List<String> columns = Arrays.asList(new String[]{"id", "Col1", "Col2","Col3"});
        List<List<String>> data = new ArrayList<>();
        data.add(Arrays.asList(new String[]{"1", "d11", "d12", "d13"}));
        data.add(Arrays.asList(new String[]{"2", "d21", "d22", "d23"}));
        data.add(Arrays.asList(new String[]{"3", "d31", "d32", "d33"}));
        System.out.println(FetchUserDataAction.buildCompleteJSON(columns, data));
    }
}
