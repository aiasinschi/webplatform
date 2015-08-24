package ro.iasinschi.webplatform.analyze.analyses.descriptive;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ro.iasinschi.webplatform.analyze.analyses.Analysis;
import ro.iasinschi.webplatform.analyze.analyses.DataModelAnalyzer;
import ro.iasinschi.webplatform.analyze.results.Result;
import ro.iasinschi.webplatform.analyze.results.descriptive.Summary;
import ro.iasinschi.webplatform.model.ColumnAnalysis;
import ro.iasinschi.webplatform.util.DatabaseUtil;
import ro.iasinschi.webplatform.util.StringListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * File SummaryAnalysis.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 2/9/2015 9:02 AM.
 */
public class SummaryAnalysis implements Analysis {

    private Log LOG = LogFactory.getLog(SummaryAnalysis.class);

    private List<List<String>> data;
    private List<String> columns;

    public SummaryAnalysis(String tableName){
        columns = DatabaseUtil.getColumns(tableName);
        data = DatabaseUtil.getDataFromTable(tableName, columns);
    }

    //TODO: call this method in the interface and return a JSON to be put in a jqGrid after
    @Override
    public Result analyze(){
        Summary summary = new Summary();
        summary.setVariables(Arrays.asList("columns", "numeric", "categorical", "missing", "num_domain", "cat_domain"));
        LOG.debug(String.format("Number of Columns = %d", columns.size()));
        for (int index = 0; index < columns.size(); index ++){
            List<String> column = StringListUtils.getColumnAtIndex(index, data);
            List<ColumnAnalysis> colAnalyses = DataModelAnalyzer.analyzeColumn(column);
            List<String> dataRow = new ArrayList<>();
            dataRow.add(columns.get(index));
            dataRow.add(String.valueOf(colAnalyses.get(2).getOccurence()));
            dataRow.add(String.valueOf(colAnalyses.get(1).getOccurence()));
            dataRow.add(String.valueOf(colAnalyses.get(0).getOccurence()));
            dataRow.add(String.valueOf(colAnalyses.get(2).getDomain()));
            dataRow.add(String.valueOf(colAnalyses.get(1).getDomain()));
            summary.addOutputLine(dataRow);
        }
        LOG.debug(String.format("Summary size = %d", summary.getOutputs() != null ? summary.getOutputs().size() : -1));
        return summary;
    }
}
