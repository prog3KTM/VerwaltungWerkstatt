# View

## Verantwortlichkeiten
- Anzeigen von Informationen f�r den Benutzer.
- Akzeptieren und Weitergeben von Benutzereingaben (optional, falls nicht im Controller verarbeitet).
- Darstellung von Statusnachrichten und Daten aus dem Model.

## Use Cases
- Anzeigen eines Hauptmen�s.
- Darstellung von Benutzerdetails.

## Code-Beispiel
```java
public class ConsoleView {

    public void showMenu() {
        System.out.println("===== Hauptmen� =====");
        System.out.println("1. Benutzer anzeigen");
        System.out.println("2. Benutzername �ndern");
        System.out.println("exit. Beenden");
        System.out.print("Bitte w�hlen: ");
    }

    public void displayUserDetails(User user) {
        System.out.println("===== Benutzerdetails =====");
        System.out.println("Name: " + user.getName());
        System.out.println("Alter: " + user.getAge());
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}