package htw.prog3.KTM.database;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;
    private List<Column> columns;
    private Column primaryKey;

    public Table(String name, List<Column> columns, Column primaryKey) {
        this.name = name;
        this.columns = columns;
        this.primaryKey = primaryKey;
    }

    public Table(String name, Column primaryKey) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public Column getPrimaryKey() {
        return primaryKey;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

}
