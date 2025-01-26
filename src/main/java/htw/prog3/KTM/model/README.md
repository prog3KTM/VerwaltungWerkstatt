# Model

## Verantwortlichkeiten
- Repräsentation der Kerndaten der Anwendung (z. B. Benutzer, Produkte).
- Verwaltung der Daten und Sicherstellung ihrer Konsistenz.
- Bereitstellung von Methoden zur Aktualisierung und Abfrage der Daten.

## Use Cases
- Speicherung und Aktualisierung von Benutzerinformationen.
- Bereitstellung von Daten, die vom Controller oder der View benötigt werden.

## Simply use Records!!!

## Code-Beispiel
```java
public class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}