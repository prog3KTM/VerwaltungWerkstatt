package htw.prog3.KTM.database;

import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

public class Column {
    private DataType<?> type;
    private String name;

    public Column(String name, DataType<?> type) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public DataType<?> getType() {
        return type;
    }
}
