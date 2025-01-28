package htw.prog3.KTM.database;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Meta;
import org.jooq.Table;
import org.jooq.impl.DSL;

import static org.jooq.impl.SQLDataType.*;

public class TableHandler {

    private DatabaseManager databaseManager;
    public TableHandler(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void checkTables() {
        DSLContext create = databaseManager.getDSLContext();

        String tableName = "Auto";
        Field<?>[] requiredColumns = {
                DSL.field("id", BIGINT.nullable(false)),
                DSL.field("model", VARCHAR(255)),
                DSL.field("brand", VARCHAR(255)),
                DSL.field("license", VARCHAR(255)),
                DSL.field("autostatus", VARCHAR(50))
        };

        // Check for table existence
        Meta meta = create.meta();
        Table<?> table = meta.getTables(tableName).stream().findFirst().orElse(null);

        if (table == null) {
            // Table does not exist, create it
            create.createTable(tableName)
                    .column("id", BIGINT.nullable(false).identity(true))
                    .column("model", VARCHAR(255))
                    .column("brand", VARCHAR(255))
                    .column("license", VARCHAR(255))
                    .column("autostatus", VARCHAR(50))
                    .constraints(
                            DSL.constraint("pk_auto").primaryKey("id")
                    )
                    .execute();
            System.out.println("Table 'Auto' created successfully.");
        } else {
            System.out.println("Table 'Auto' already exists. Checking columns...");

            // Check for missing columns
            for (Field<?> requiredColumn : requiredColumns) {
                String columnName = requiredColumn.getName();
                boolean columnExists = table.field(columnName) != null;

                if (!columnExists) {
                    create.alterTable(tableName)
                            .addColumn(columnName, requiredColumn.getDataType())
                            .execute();
                    System.out.printf("Column '%s' added to table 'Auto'.%n", columnName);
                } else {
                    System.out.printf("Column '%s' already exists in table 'Auto'.%n", columnName);
                }
            }
        }

    }

    public void dropTables() {
        databaseManager.getDSLContext().dropTable("Auto").execute();
    }
}
