package ro.iasinschi.webplatform.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ro.iasinschi.webplatform.model.DataColumn;

import javax.persistence.Query;
import java.util.List;

/**
 * File DataColumnService.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/11/2015 11:58 AM.
 */
public class DataColumnService extends SimpleService{

    private static final Log LOG = LogFactory.getLog(DataColumnService.class);

    public static List<DataColumn> getColumnsForTableID(long tableID){
        if (!getTransaction().isActive()) {
            getTransaction().begin();
        }
        String sql = "SELECT * FROM datacolumn WHERE tableid = ?";
        Query query = getManager().createNativeQuery(sql, DataColumn.class);
        query.setParameter(1, tableID);
        // assume user has only one table
        List<DataColumn> dataColumnList = query.getResultList();
        getTransaction().commit();
        return dataColumnList;
    }

    public static void addColumn(DataColumn dataColumn){
        if (!getTransaction().isActive()) {
            getTransaction().begin();
        }
        getManager().persist(dataColumn);
        getTransaction().commit();
    }

    public static void addColumnSet(List<DataColumn> columnList){
        if (!getTransaction().isActive()) {
            getTransaction().begin();
        }
        for (DataColumn dataColumn: columnList) {
            getManager().persist(dataColumn);
        }
        getTransaction().commit();
    }
}
