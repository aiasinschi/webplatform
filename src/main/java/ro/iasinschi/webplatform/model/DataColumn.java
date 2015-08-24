package ro.iasinschi.webplatform.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * File DataColumn.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/10/2015 7:19 PM.
 */
@Entity
public class DataColumn {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private int type;

    private long tableid;

    public DataColumn() {
    }

    public DataColumn(long tableid, String name, int type) {
        this.name = name;
        this.type = type;
        this.tableid = tableid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTableid() {
        return tableid;
    }

    public void setTableid(long usertableid) {
        this.tableid = usertableid;
    }
}
