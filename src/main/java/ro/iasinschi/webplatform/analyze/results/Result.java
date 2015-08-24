package ro.iasinschi.webplatform.analyze.results;

import java.util.ArrayList;
import java.util.List;

/**
 * File Result.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 2/9/2015 8:04 AM.
 */
public abstract class Result {

    protected List<String> variables;

    protected List<List<String>> outputs;

    protected ResultType type;

    public List<String> getVariables(){
        return variables;
    }

    public void setVariables(List<String> variables){
        this.variables = variables;
    }

    public List<List<String>> getOutputs(){
        return outputs;
    }

    public void addOutputLine(List<String> outputLine){
        if (getOutputs() == null){
            outputs = new ArrayList<>();
        }
        this.getOutputs().add(outputLine);
    }

    public ResultType getType(){
        return type;
    }

    public void setType(ResultType type){
        this.type = type;
    }

    public abstract String toJSONResult();

}
