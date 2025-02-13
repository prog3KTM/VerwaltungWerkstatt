package htw.prog3.KTM.view;

import htw.prog3.KTM.model.kunde.Kunde;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;
import htw.prog3.KTM.service.WerkstattInformationService;

import java.util.Scanner;

public class TextLineInterface implements MenuInteractions {

    public TextLineInterface() { }

    @Override
    public void showMainMenu() {
        System.out.println("======= HauptMenü =======");
        System.out.println("1.  WerkstattInformationen ansehen.");
        System.out.println("2.  KundenMenü ansehen.");
        System.out.println("99. Programm beenden.");
    }

    @Override
    public void showKunde(Kunde kunde) {
        System.out.println("Kunde: " + kunde.getName());
    }

    @Override
    public void showWerk() {
        System.out.println("Werk: " + WerkstattInformationService.getInstance().getWerkstattInformation().toString());
        getScanner().next();
    }

    @Override
    public void showKundenMenu() {
        System.out.println("======= KundenMenu =======");
        System.out.println("1. ...");
    }

    @Override
    public WerkstattInformation getWerkstattInformation() {
        return null;
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

    public Scanner getScanner() {
        return new Scanner(System.in);
    }

}
