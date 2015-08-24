package ro.iasinschi.webplatform.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ro.iasinschi.webplatform.analyze.analyses.descriptive.SummaryAnalysis;
import ro.iasinschi.webplatform.analyze.results.Result;
import ro.iasinschi.webplatform.model.PlatformUser;
import ro.iasinschi.webplatform.util.ApplicationContext;
import ro.iasinschi.webplatform.util.DatabaseUtil;

import java.util.List;

/**
 * File TableSummaryAction.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 2/12/2015 9:11 PM.
 */
public class TableSummaryAction extends ActionSupport implements Action {

    private static Log LOG = LogFactory.getLog(TableSummaryAction.class);

    private Long userId;

    private String tableName;

    private String jsonResult;

    @Override
    public String execute(){
        PlatformUser user = ApplicationContext.getLoggedUser();
        if (user == null){
            return Action.LOGIN;
        }
        List<String> columns = DatabaseUtil.getColumns(tableName);
        List<List<String>> data = DatabaseUtil.getDataFromTable(tableName, columns);
        SummaryAnalysis analysis = new SummaryAnalysis(tableName);
        Result summary = analysis.analyze();
        jsonResult = summary.toJSONResult();
        return Action.SUCCESS;
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
}
