package htw.prog3.KTM.view;

import htw.prog3.KTM.controller.WerkstattInformationController;
import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.kunde.Kunde;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;

import java.util.Scanner;

public class TextLineInterface implements MenuInteractions {

    DatabaseManager databaseManager;
    public TextLineInterface(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void showMainMenu() {
        System.out.println("======= HauptMenü =======");
        System.out.println("1.  WerkstattInformationen ansehen.");
        System.out.println("2.  KundenMenü ansehen.");
        System.out.println("3.  AutoMenü ansehen.");
        System.out.println("4.  ServiceMenü ansehen.");
        System.out.println("99. Programm beenden.");
    }

    @Override
    public void showKunde(Kunde kunde) {
        System.out.println("Kunde: " + kunde.getName());
    }

    @Override
    public void showWerk() {
        System.out.println("Werk: " + new WerkstattInformationController(databaseManager).getWerkstattInformation().toString());
        getScanner().next();
    }

    @Override
    public void showKundenMenu() {
        System.out.println("======= KundenMenu =======");
        System.out.println("1. ...");
    }

    @Override
    public void showAutoMenu() {
        System.out.println("======= AutoMenu =======");
        System.out.println("1. ...");
    }

    @Override
    public void showServiceMenu() {
        System.out.println("======= ServiceMenu =======");
        System.out.println("1. ...");
    }

    @Override
    public WerkstattInformation getWerkstattInformation() {
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

        return new WerkstattInformation(namen, location, phone, email, website, vat, busregnumber, iban);
    }

    private String getString(String prompt) {
        System.out.println(prompt);
        Scanner scanner = getScanner();
        return scanner.nextLine();
    }

    private int getInt(String prompt) {
        System.out.println(prompt);
        Scanner scanner = getScanner();
        String input = scanner.nextLine();
        if(input.matches("[0-9]+") && input.length() < 16) {
            return Integer.parseInt(input);
        }else {
            System.out.println("Du musst eine Zahl angeben.");
            return getInt(prompt);
        }
    }

    @Override
    public int getOption() {
        Scanner scanner = getScanner();
        System.out.println("Bitte wähle eine Option: ");
        if(scanner.hasNextInt()) return scanner.nextInt();
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
        return new Scanner(System.in);
    }

}
