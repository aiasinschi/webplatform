package ro.iasinschi.webplatform.analyze.results;

/**
 * File ResultType.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 2/9/2015 8:11 AM.
 */
public enum ResultType {

    TABLE(0, "table"),

    GRAPH(1, "graph");

    private int type;

    private String name;

    ResultType(int type, String name){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
