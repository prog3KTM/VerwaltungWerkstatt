package htw.prog3.KTM.service;

import htw.prog3.KTM.controller.WerkstattInformationController;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;
import htw.prog3.KTM.util.main;
import htw.prog3.KTM.view.MenuInteractions;
import htw.prog3.KTM.view.TextLineInterface;

public class MenuService {

    private static MenuService instance;

    private MenuInteractions menu;
    private boolean running;
    private MenuService() {
        menu = new TextLineInterface(main.getAppConfig().getDatabaseManager());
        running = true;
    }

    public void run() {
        while (running) {
            checkForWerkstattInformation();
            menu.showMainMenu();
            int option = menu.getOption();
            switch (option) {
                case 1:
                    menu.showWerk();
                    break;
                case 2:
                    runKundenMenuLogic();
                    break;
                case 3:
                    runAutoMenuLogic();
                    break;
                case 4:
                    runServiceMenuLogic();
                    break;
                case 99:
                    running = false;
                    break;
                default:
                    menu.throwError("Invalid option.");
                    break;
            }
        }
    }

    private void runServiceMenuLogic() {
        menu.showServiceMenu();
        int option = menu.getOption();
        switch (option) {
            default:
                //TODO
                break;
        }
    }

    private void runAutoMenuLogic() {
        menu.showAutoMenu();
        int option = menu.getOption();
        switch (option) {
            default:
                //TODO
                break;
        }
    }

    private void runKundenMenuLogic() {
        menu.showKundenMenu();
        int option = menu.getOption();
        switch (option) {
            default:
                //TODO
                break;
        }
    }

    private void checkForWerkstattInformation() {
        WerkstattInformationController werkstattInformationController = new WerkstattInformationController(main.getAppConfig().getDatabaseManager());
        if(!werkstattInformationController.ifInformationExists()) {
            WerkstattInformation werkstattInformation = menu.getWerkstattInformation();
            werkstattInformationController.save(werkstattInformation);
            menu.sendMessage("Werkstatt-Information wurde gespeichert!");
        }
    }

    public static MenuService getInstance() {
        if(instance == null) instance = new MenuService();
        return instance;
    }

}
