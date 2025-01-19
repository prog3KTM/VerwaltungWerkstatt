# Controller

## Verantwortlichkeiten
- Verarbeiten von Benutzereingaben (z. B. über die Konsole).
- Steuerung der Anwendungslogik, indem Methoden aus der Model- und View-Schicht aufgerufen werden.
- Sicherstellen, dass die View die richtigen Daten vom Model erhält.

## Use Cases
- Aufrufen von CRUD-Operationen (z. B. "Erstelle einen Benutzer", "Zeige eine Liste von Benutzern").
- Verarbeiten von Kommandos wie "exit", "add user", "delete user".

## Code-Beispiel
```java
import java.util.Scanner;

public class UserController {
    private final User userModel;
    private final ConsoleView view;

    public UserController(User userModel, ConsoleView view) {
        this.userModel = userModel;
        this.view = view;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            view.showMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    view.displayUserDetails(userModel);
                    break;
                case "2":
                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine();
                    userModel.setName(name);
                    view.showMessage("Name updated!");
                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    view.showMessage("Ungültiger Befehl!");
            }
        }
    }
}
