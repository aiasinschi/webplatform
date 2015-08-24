package ro.iasinschi.webplatform.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ro.iasinschi.webplatform.util.exceptions.BadTableException;

import java.util.ArrayList;
import java.util.List;

/**
 * File StringListUtils.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/31/2015 5:15 PM.
 */
public class StringListUtils {

    private static Log LOG = LogFactory.getLog(StringListUtils.class);

    public static List<String> getColumnAtIndex(int index, List<List<String>> table){
        List<String> result = new ArrayList<>();
        int max = getMaxColumnIndex(table);
        if ((index >= max) || (index < 0)){
            LOG.warn("Trying to access columns beyond the table's dimensions");
            return result;
        }
        for (int i = 0; i < table.size(); i++){
            result.add(table.get(i).get(index));
        }
        return result;
    }

    private static int getMaxColumnIndex(List<List<String>> table){
        if ((table == null) || (table.size() == 0)){
            return 0;
        }
        int max = table.get(0).size();
        for (List<String> row: table){
            if (row.size() != max){
                String message = String.format("Bad table! Row %d has length %d while first row has length %d.", table.indexOf(row), row.size(), max);
                LOG.debug(message);
                throw new BadTableException(message);
            }
        }
        return max;
    }
}
