package ro.iasinschi.webplatform.actions;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.struts2.interceptor.SessionAware;
import ro.iasinschi.webplatform.model.UserData;
import ro.iasinschi.webplatform.services.UserDataService;
import ro.iasinschi.webplatform.util.ApplicationContext;
import ro.iasinschi.webplatform.util.DatabaseUtil;
import ro.iasinschi.webplatform.util.constants.RoleType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * File ExcelImportAction.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/6/2015 8:52 AM.
 */
public class ExcelImportAction extends ActionSupport implements Action, SessionAware {

    private Log LOG = LogFactory.getLog(ExcelImportAction.class);

    private File fileUpload;

    private String fileUploadContentType;

    private String fileUploadFileName;

    private String submit;

    @Override
    public String execute(){
        if (ApplicationContext.getLoggedUser() == null){
            return Action.LOGIN;
        }
        LOG.debug(String.format("File uploaded in Action: %s", fileUpload.getAbsolutePath()));
        List<String> columns = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        try(OPCPackage pack = OPCPackage.open(fileUpload)){
            Workbook workbook = WorkbookFactory.create(pack);
            //TODO: whole lotta NPE checks
            Sheet sheet = workbook.getSheetAt(0);
            Row hRow = sheet.getRow(0);
            for (Cell cell: hRow){
                columns.add(cell.getStringCellValue());
            }
            for (int i = 1; i <= sheet.getLastRowNum(); i++){
                Row row = sheet.getRow(i);
                List<String> rowData = new ArrayList<>();
                for (Cell cell: row){
                    rowData.add(getStringValueForCell(cell));
                }
                data.add(rowData);
            }
        } catch (Exception ex) {
            System.err.println("Error while reading from Excel file" + ex.toString());
        }
        if ((columns.size() > 0) && (data.size() > 0)) {
            long userId = ApplicationContext.getLoggedUser().getId();

            UserData userData = new UserData(userId, getNewTableNameForLoggedUser(userId), RoleType.USER.getValue());
            UserDataService.addUserData(userData);
            DatabaseUtil.createTable(userData.getTablename(), columns);
            DatabaseUtil.addDataToTable(userData.getTablename(), columns, data);
        }
        return Action.SUCCESS;
    }

    private String getNewTableNameForLoggedUser(long id){
        List<UserData> userDataList = UserDataService.getUserDataForUserID(id);
        return String.format("u_dat_%s_%s", ApplicationContext.getLoggedUser().getUsername(), (userDataList.size() + 1));
    }


    private String getStringValueForCell(Cell cell){
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            return Double.toString(cell.getNumericCellValue());
        } else {
            return cell.getStringCellValue();
        }
    }

    @Override
    public void setSession(Map<String, Object> map) {

    }

    public File getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(File fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }

    public String getFileUploadContentType() {
        return fileUploadContentType;
    }

    public void setFileUploadContentType(String fileUploadContentType) {
        this.fileUploadContentType = fileUploadContentType;
    }

    public String getFileUploadFileName() {
        return fileUploadFileName;
    }

    public void setFileUploadFileName(String fileUploadFileName) {
        this.fileUploadFileName = fileUploadFileName;
    }
}
