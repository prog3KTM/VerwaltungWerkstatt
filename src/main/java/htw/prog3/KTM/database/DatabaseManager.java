package htw.prog3.KTM.database;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:werkstatt.db"; // SQLite-Datenbank-URL
    private static final String USERNAME = ""; // Falls notwendig, Benutzername
    private static final String PASSWORD = ""; // Falls notwendig, Passwort
    private static Connection CONNECTION = null;

    public DatabaseManager() {
        try {
            CONNECTION = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to database", e);
        }
    }

    // Stellt eine Verbindung zur SQLite-Datenbank her
    public Connection getConnection() {
        return CONNECTION;
    }

    // Stellt einen DSLContext bereit, der für die Interaktion mit der Datenbank verwendet wird
    public DSLContext getDSLContext() throws SQLException {
        return DSL.using(CONNECTION);
    }
}
