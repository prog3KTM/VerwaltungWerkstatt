package htw.prog3.KTM.util;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.LogManager;

import static test.generated.Tables.KUNDE;

public class main {
    public static void main(String[] args) {
        System.setProperty("org.jooq.no-logo", "true");
        // Disable jOOQ logging
        LogManager.getLogManager().reset();
        String userName = "";
        String password = "";
        String url = "jdbc:sqlite:werkstatt.db";


        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.SQLITE);
            Result<org.jooq.Record> result = create.select().from(KUNDE).fetch();
            for (Record r : result) {
                Integer id = r.getValue(KUNDE.ID);
                String name = r.getValue(KUNDE.NAME);
                String phone = r.getValue(KUNDE.PHONE);

                System.out.println("ID: " + id + " name: " + name + " phone: " + phone);
            }
        }


        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
