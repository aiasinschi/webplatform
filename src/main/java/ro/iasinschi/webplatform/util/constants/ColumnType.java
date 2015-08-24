package ro.iasinschi.webplatform.util.constants;

/**
 * File ColumnType.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/10/2015 7:10 PM.
 */
public enum ColumnType {

    MISSING(0, "Missing"),

    SCALAR(1, "Scalar"),

    CATEGORICAL(2, "Categorical"),

    NUMERIC(3, "Numeric");

    private int value;

    private String description;

    private ColumnType(int value, String description){
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
