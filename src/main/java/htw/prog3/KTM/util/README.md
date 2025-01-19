# Util

## Verantwortlichkeiten
- Enthält Hilfsklassen und -methoden, die in der gesamten Anwendung verwendet werden.
- Zentralisierung von Logik, die unabhängig von der Geschäftslogik ist (z. B. Datenbankverbindungen, Logging).

## Use Cases
- Bereitstellung einer Verbindung zu einer Datenbank.
- Wiederverwendbare Methoden, die von mehreren Schichten genutzt werden.

## Code-Beispiel
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Verbinden mit der Datenbank", e);
        }
    }
}
