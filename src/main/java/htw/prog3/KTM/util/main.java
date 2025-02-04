package htw.prog3.KTM.util;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.controller.KundeController;
import htw.prog3.KTM.model.kunde.Kunde;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.LogManager;


public class main {

    private static AppConfig appConfig;

    public static void main(String[] args) {
        System.setProperty("org.jooq.no-logo", "true");
        // Disable jOOQ logging
        LogManager.getLogManager().reset();
        String userName = "";
        String password = "";
        String url = "jdbc:sqlite:werkstatt.db";

        // DI wird durch die AppConfig-Klasse gehandhabt
        appConfig = new AppConfig();
        KundeController kundeController = appConfig.getKundeController();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nKundenverwaltung:");
            System.out.println("1. Alle Kunden anzeigen");
            System.out.println("2. Kunden nach ID suchen");
            System.out.println("3. Neuen Kunden hinzufügen");
            System.out.println("4. Kunden aktualisieren");
            System.out.println("5. Kunden löschen");
            System.out.println("6. Beenden");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Puffer leeren

            try {
                switch (choice) {
                    case 1:
                        List<Kunde> kunden = kundeController.getAllKunden();
                        kunden.forEach(k -> System.out.println(k.getId() + ": " + k.getName()));
                        break;
                    case 2:
                        System.out.print("Kunden-ID eingeben: ");
                        int id = scanner.nextInt();
                        Optional<Kunde> kundeOpt = kundeController.getKundeById(id);
                        kundeOpt.ifPresent(k -> System.out.println(k));
                        break;
                    case 3:
                        // Benutzerabfrage für neuen Kunden
                        break;
                    case 4:
                        // Benutzerabfrage für Kundenaktualisierung
                        break;
                    case 5:
                        // Benutzerabfrage für Kundenlöschung
                        break;
                    case 6:
                        running = false;
                        System.out.println("Beenden...");
                        break;
                    default:
                        System.out.println("Ungültige Wahl!");
                }
            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static AppConfig getAppConfig() {
        return appConfig;
    }
}

