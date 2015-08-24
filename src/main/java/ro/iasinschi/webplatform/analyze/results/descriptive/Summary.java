package ro.iasinschi.webplatform.analyze.results.descriptive;

import ro.iasinschi.webplatform.analyze.results.Result;
import ro.iasinschi.webplatform.analyze.results.ResultType;

import java.util.List;

/**
 * File Summary.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 2/9/2015 8:21 AM.
 */
public class Summary extends Result{

    public Summary(){
        setType(ResultType.TABLE);
    }

    @Override
    public String toJSONResult() {
        StringBuilder colnames = new StringBuilder("[ ");
        StringBuilder headers = new StringBuilder("[ ");
        StringBuilder values = new StringBuilder("[ ");
        for (String header: getVariables()){
            headers.append(String.format("{\"name\": \"%s\", \"index\": \"%s\", \"width\": 100},", header, header)); //colModel: [ {name:'name1', index:'index1'...}, {...}, ... ],
            colnames.append("\"" + header + "\"");
            colnames.append(", ");
        }
        headers = new StringBuilder(headers.subSequence(0, headers.lastIndexOf(",")));
        colnames = new StringBuilder(colnames.subSequence(0, colnames.lastIndexOf(",")));
        headers.append("]");
        colnames.append("]");
        for (List<String> row: getOutputs()){
            values.append("{");
            for (int i = 0; i < getVariables().size() - 1; i++){
                values.append(String.format("\"%s\": \"%s\", ", getVariables().get(i), row.get(i)));
            }
            values.append(String.format("\"%s\": \"%s\" ", getVariables().get( getVariables().size() - 1), row.get( getVariables().size() - 1)));
            values.append("},");
        }
        values = new StringBuilder(values.subSequence(0, values.lastIndexOf(",")));
        values.append("]");
        return String.format("{ \"colnames\": %s, \"headers\": %s, \"values\": %s, \"type\": \"%s\"}", colnames.toString(), headers.toString(), values.toString(), getType().getName());
    }
}
