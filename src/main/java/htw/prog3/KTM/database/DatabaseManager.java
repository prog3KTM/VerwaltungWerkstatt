package htw.prog3.KTM.database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static String URL; // SQLite-Datenbank-URL
    private static final String USERNAME = ""; // Falls notwendig, Benutzername
    private static final String PASSWORD = ""; // Falls notwendig, Passwort
    private static Connection CONNECTION = null;

    public DatabaseManager() {
        URL = "jdbc:sqlite:werkstatt.db";
        connect();
    }

    public DatabaseManager(String databaseName) {
        URL = "jdbc:sqlite:"+databaseName;
        connect();
    }

    private void connect() {
        try {
            if (CONNECTION == null || CONNECTION.isClosed()) {
                CONNECTION = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to database", e);
        }
    }

    // Stellt eine Verbindung zur SQLite-Datenbank her
    public static Connection getConnection() {
        try {
            if (CONNECTION == null || CONNECTION.isClosed()) {
                CONNECTION = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to database", e);
        }
        return CONNECTION;
    }

    // Stellt einen DSLContext bereit, der fuer die Interaktion mit der Datenbank verwendet wird
    public DSLContext getDSLContext() {
        connect(); // Ensure connection is open
        return DSL.using(CONNECTION, SQLDialect.SQLITE);
    }
    
    // Close the connection when the application is shutting down
    public void closeConnection() {
        if (CONNECTION != null) {
            try {
                CONNECTION.close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }
}
