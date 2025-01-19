# Service

## Verantwortlichkeiten
- Vermittlung zwischen der Controller- und der Repository-Schicht.
- Enthält die Geschäftslogik der Anwendung.
- Aggregiert oder transformiert Daten aus den Repositories, bevor sie an den Controller zurückgegeben werden.
- Validiert Eingaben und ruft Repository-Methoden auf.

## Use Cases
- Validierung von Benutzereingaben.
- Verwaltung von Abläufen wie der Erstellung eines Benutzers, Aktualisierung von Daten, etc.
- Weiterleitung von Daten zwischen Controller und Repository.

## Code-Beispiel
```java
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID " + id + " nicht gefunden"));
    }

    public void addUser(User user) {
        // Beispiel: Eingabevalidierung
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Name darf nicht leer sein");
        }
        userRepository.save(user);
    }
}
