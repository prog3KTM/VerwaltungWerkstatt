package htw.prog3.KTM.util;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.controller.KundeController;
import htw.prog3.KTM.generated.tables.records.KundeRecord;
import htw.prog3.KTM.model.Kunde;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.LogManager;

import static htw.prog3.KTM.generated.Tables.KUNDE;


public class main {
    public static void main(String[] args) {
        System.setProperty("org.jooq.no-logo", "true");
        // Disable jOOQ logging
        LogManager.getLogManager().reset();
        String userName = "";
        String password = "";
        String url = "jdbc:sqlite:werkstatt.db";

        // DI wird durch die AppConfig-Klasse gehandhabt
        AppConfig appConfig = new AppConfig();
        KundeController kundeController = appConfig.getKundeController();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nKundenverwaltung:");
            System.out.println("1. Alle Kunden anzeigen");
            System.out.println("2. Kunden nach ID suchen");
            System.out.println("3. Neuen Kunden hinzuf�gen");
            System.out.println("4. Kunden aktualisieren");
            System.out.println("5. Kunden l�schen");
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
                        // Benutzerabfrage f�r neuen Kunden
                        break;
                    case 4:
                        // Benutzerabfrage f�r Kundenaktualisierung
                        break;
                    case 5:
                        // Benutzerabfrage f�r Kundenl�schung
                        break;
                    case 6:
                        running = false;
                        System.out.println("Beenden...");
                        break;
                    default:
                        System.out.println("Ung�ltige Wahl!");
                }
            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e.getMessage());
            }
        }

        scanner.close();
    }
}

