package htw.prog3.KTM.view;

import htw.prog3.KTM.controller.WorkshopInformationController;
import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.customer.Customer;
import htw.prog3.KTM.model.jobs.Service;
import htw.prog3.KTM.model.order.Order;
import htw.prog3.KTM.model.order.OrderStatus;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TextLineInterface implements MenuInteractions {

    DatabaseManager databaseManager;
    private Scanner scanner;
    
    public TextLineInterface(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMainMenu() {
        System.out.println("======= HauptMenü =======");
        System.out.println("1.  WerkstattInformationen ansehen.");
        System.out.println("2.  KundenMenu ansehen.");
        System.out.println("3.  CarMenü ansehen.");
        System.out.println("4.  ServiceMenü ansehen.");
        System.out.println("5.  OrderMenü ansehen.");
        System.out.println("99. Programm beenden.");
    }

    @Override
    public void showKunde(Customer customer) {
        System.out.println("Kunde: " + customer.getName());
        System.out.println("ID: " + customer.getId());
        System.out.println("Adresse: " + customer.getAddress());
        System.out.println("Telefon: " + customer.getPhone());
        if (!customer.getCars().isEmpty()) {
            System.out.println("Autos:");
            customer.getCars().forEach(auto -> System.out.println("  - " + auto.toString()));
        }
    }

    @Override
    public void showWerk(WorkshopInformation workshopInformation) {
        System.out.println("Name: " + workshopInformation.name());
        System.out.println("Email: " + workshopInformation.email());
        System.out.println("Telefon: " + workshopInformation.phone());
        System.out.println("Ort: " + workshopInformation.location());
        System.out.println("Webseite: " + workshopInformation.website());
        System.out.println("Iban: " + workshopInformation.iban());
        System.out.println("Vat: " + workshopInformation.vat());
        System.out.println("Busregnumber: " + workshopInformation.businessRegNumber());
        scanner.nextLine();
    }

    @Override
    public void showKundenMenu() {
        System.out.println("======= KundenMenu =======");
        System.out.println("1. Alle Kunden anzeigen");
        System.out.println("2. Kunde nach ID suchen");
        System.out.println("3. Neuen Kunden hinzufügen");
        System.out.println("4. Kunden aktualisieren");
        System.out.println("5. Kunden löschen");
        System.out.println("9. Zurück zum Hauptmenü");
    }

    @Override
    public void showCarMenu() {
        System.out.println("======= CarMenu =======");
        System.out.println("1. Alle Autos anzeigen");
        System.out.println("2. Auto nach ID suchen");
        System.out.println("3. Neues Auto hinzufügen");
        System.out.println("4. Auto löschen");
        System.out.println("5. Auto-Status aktualisieren");
        System.out.println("9. Zurück zum Hauptmenü");
    }

    @Override
    public void showServiceMenu() {
        System.out.println("======= ServiceMenu =======");
        System.out.println("1. Neuen Service für Auto hinzufügen");
        System.out.println("2. Alle Services anzeigen");
        System.out.println("3. Service nach ID suchen");
        System.out.println("4. Service Status aktualisieren");
        System.out.println("9. Zurück zum Hauptmenü");
    }

    @Override
    public void showOrderMenu() {
        System.out.println("======= OrderMenu =======");
        System.out.println("1. Neue Order für Kunden erstellen");
        System.out.println("2. Alle Order anzeigen");
        System.out.println("3. Order nach ID suchen");
        System.out.println("4. Order Status aktualisieren");
        System.out.println("5. Order-Preis bearbeiten");
        System.out.println("6. Service zu Order hinzufügen");
        System.out.println("9. Zurück zum Hauptmenü");
    }

    @Override
    public WorkshopInformation getWerkstattInformation() {
        String namen = "";
        String location = "";
        int phone = 0;
        String email = "";
        String website = "";
        String vat = "";
        String busregnumber = "";
        String iban = "";

        namen = getString("Bitte gib den Namen deiner Werkstatt an:");
        location = getString("Bitte gib den Location deiner Werkstatt an:");
        phone = getInt("Bitte gib die Telefonnummer deiner Werkstatt an:");
        email = getString("Bitte gib den Email deiner Werkstatt an:");
        website = getString("Bitte gib den Website deiner Werkstatt an:");
        vat = getString("Bitte gib den VAT deiner Werkstatt an:");
        busregnumber = getString("Bitte gib die Businessregistrationnumber deiner Werkstatt an:");
        iban = getString("Bitte gib den IBAN deiner Werkstatt an:");

        return new WorkshopInformation(namen, location, phone, email, website, vat, busregnumber, iban);
    }

    @Override
    public String getString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public int getInt(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        if(input.matches("[0-9]+") && input.length() < 16) {
            return Integer.parseInt(input);
        }else {
            System.out.println("Du musst eine Zahl angeben.");
            return getInt(prompt);
        }
    }

    @Override
    public void showOrder(Optional<Order> order) {
        if(order.isPresent()) {
            Order o = order.get();
            System.out.println("======= Order =======");
            System.out.println("Erstelldatum: " + o.getOrderDate().toLocalDate());
            System.out.print("Kunde: ");
            o.getCustomer().ifPresentOrElse(customer -> {
                        System.out.println(customer.getName());
                    }, () -> {
                        System.out.println("Fehler beim laden des Kunden.");
                    });
            System.out.println("Status: " + o.getStatus());
            System.out.println("Summe (Netto): " + String.format("%.2f", o.getTotal()));
            System.out.println("Summe (inkl MWST): " + String.format("%.2f", o.getTotalWithTaxes()));
            System.out.println();
            System.out.println("Erledigte Services: ");
            for(Service service : o.getServices()) {
                System.out.println(" - "+service.getName() + " ["+service.getTypeString()+"]");
            }
        }else {
            System.out.println("Diese Order existiert nicht.");
        }
    }

    @Override
    public void showAllOrder(List<Order> orders) {
        for(Order order : orders) {
            System.out.println(order.getId() + ". " + order.getCustomerId() + " : " + order.getOrderDate().toLocalDate() + " ["+order.getStatus()+"]");
        }
    }

    @Override
    public void hold() {
        getString("Tippe irgendwas ein um fortzufahren.");
    }

    @Override
    public float getFloat(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        if(input.matches("[0.0-9.9]+") && input.length() < 16) {
            return Float.parseFloat(input);
        }else {
            System.out.println("Du musst eine Dezimalzahl angeben.");
            return getFloat(prompt);
        }
    }

    @Override
    public OrderStatus getOrderStatus(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        if(Arrays.stream(OrderStatus.values())
                .anyMatch(status -> status.toString().equalsIgnoreCase(input))) {
            return OrderStatus.valueOf(input.toUpperCase());
        }else {
            throwError("Dieser Status existiert nicht.");
            return getOrderStatus(prompt);
        }
    }

    @Override
    public int getOption() {
        System.out.println("Bitte wähle eine Option: ");
        if(scanner.hasNextInt()) {
            int option = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer
            return option;
        }
        scanner.nextLine(); // Clear the buffer
        return 0;
    }

    @Override
    public void throwError(String err) {
        System.out.println(err);
    }

    @Override
    public void sendMessage(String msg) {
        System.out.println(msg);
    }

    public Scanner getScanner() {
        return scanner;
    }
    
    // Methods to get customer information
    public Customer getKundeInfo() {
        System.out.println("=== Neuen Kunden anlegen ===");
        int id = getInt("Kunden-ID eingeben:");
        String name = getString("Name eingeben:");
        String address = getString("Adresse eingeben:");
        String phone = getString("Telefonnummer eingeben:");
        
        return new Customer(id, name, address, phone);
    }
    
    // Methods to get car information
    public String getCarInfo() {
        System.out.println("=== Neues Auto anlegen ===");
        String id = getString("Auto-ID eingeben:");
        String model = getString("Modell eingeben:");
        String brand = getString("Marke eingeben:");
        String licensePlate = getString("Kennzeichen eingeben:");
        return id + "," + model + "," + brand + "," + licensePlate;
    }
    
    // Methods to get service information
    public String getServiceInfo() {
        System.out.println("=== Neuen Service anlegen ===");
        int jobId = getInt("Service-ID eingeben:");
        
        System.out.println("Verfügbare Service-Typen:");
        System.out.println("1. Ölwechsel (OIL_CHANGE)");
        System.out.println("2. Reifenwechsel (TIRE_REPLACEMENT)");
        System.out.println("3. Software-Update (SOFTWARE_UPDATE)");
        System.out.println("4. Bremsenreparatur (BRAKE_REPAIR)");
        System.out.println("5. Motor-Diagnose (ENGINE_DIAGNOSTIC)");
        System.out.println("6. Allgemeine Wartung (GENERAL_MAINTENANCE)");
        
        int typeChoice = getInt("Service-Typ wählen (1-6):");
        if (typeChoice < 1 || typeChoice > 6) {
            System.out.println("Ungültige Auswahl. Verwende Allgemeine Wartung als Standard.");
            typeChoice = 6;
        }
        
        String jobName = getString("Beschreibung eingeben:");
        
        return jobId + "," + typeChoice + "," + jobName;
    }
}
