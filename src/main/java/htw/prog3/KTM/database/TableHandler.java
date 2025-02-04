package htw.prog3.KTM.database;

import org.jooq.*;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

public class TableHandler {

    private DatabaseManager databaseManager;
    private List<htw.prog3.KTM.database.Table> tables;
    public TableHandler(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        tables = new ArrayList<>();
    }

    public void checkTables() {
        DSLContext create = databaseManager.getDSLContext();
        for(htw.prog3.KTM.database.Table t : tables) {
            List<Field<?>> requiredColumns = new ArrayList<>();
            for(Column col : t.getColumns()) {
                requiredColumns.add(DSL.field(col.getName(), col.getType()));
            }
            requiredColumns.add(DSL.field(t.getPrimaryKey().getName(), t.getPrimaryKey().getType()));

            Meta meta = create.meta();
            Table<?> table = meta.getTables(t.getName()).stream().findFirst().orElse(null);

            if (table == null) {
                CreateTableElementListStep step = create.createTable(t.getName());
                for(Field<?> f : requiredColumns) {
                    step = step.column(f);
                }
                step = step.constraints(
                        DSL.constraint("pk_"+t.getName().toLowerCase()).primaryKey(t.getPrimaryKey().getName())
                );
                step.execute();
                System.out.println("Table '"+t.getName()+"' created successfully.");
            } else {
                System.out.println("Table '"+t.getName()+"' already exists. Checking columns...");

                for (Field<?> requiredColumn : requiredColumns) {
                    String columnName = requiredColumn.getName();
                    boolean columnExists = table.field(columnName) != null;

                    if (!columnExists) {
                        create.alterTable(t.getName())
                                .addColumn(columnName, requiredColumn.getDataType())
                                .execute();
                        System.out.printf("Column '%s' added to table '"+t.getName()+"'.%n", columnName);
                    } else {
                        System.out.printf("Column '%s' already exists in table '"+t.getName()+"'.%n", columnName);
                    }
                }
            }
        }
    }

    public void addTable(htw.prog3.KTM.database.Table table) {
        tables.add(table);
    }

    public void dropTables() {
        for(htw.prog3.KTM.database.Table t : tables) {
            databaseManager.getDSLContext().dropTable(t.getName()).execute();
        }
    }
}
