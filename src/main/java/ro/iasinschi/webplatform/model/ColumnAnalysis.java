package ro.iasinschi.webplatform.model;

import ro.iasinschi.webplatform.util.constants.ColumnType;

import java.util.Set;

/**
 * File ColumnAnalysis.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/31/2015 5:12 PM.
 */
public class ColumnAnalysis {

    private ColumnType type;

    private double occurence;

    private Set<String> domain;

    public ColumnAnalysis(ColumnType type, double occurence, Set<String> domain) {
        this.type = type;
        this.occurence = occurence;
        this.domain = domain;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public double getOccurence() {
        return occurence;
    }

    public void setOccurence(double occurence) {
        this.occurence = occurence;
    }

    public Set<String> getDomain() {
        return domain;
    }

    public void setDomain(Set<String> domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "ColumnAnalysis{" +
                "type=" + type +
                ", occurence=" + occurence +
                "%, domain=" + domain +
                '}';
    }
}
