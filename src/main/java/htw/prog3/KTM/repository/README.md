# Repository

## Verantwortlichkeiten
- Direkter Zugriff auf die Datenquelle (z. B. Datenbank).
- Definiert CRUD-Operationen (Create, Read, Update, Delete) für Domänenobjekte.
- Abfrage und Speichern von Daten im Model.

## Use Cases
- Speichern eines Benutzers in der Datenbank.
- Abfragen eines Benutzers basierend auf seiner ID.

## Code-Beispiel
```java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public Optional<User> findById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public void save(User user) {
        users.add(user);
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}
