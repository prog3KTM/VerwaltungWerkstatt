package htw.prog3.KTM.service;

import htw.prog3.KTM.view.MenuInteractions;
import htw.prog3.KTM.view.TextLineInterface;

public class MenuService {

    private static MenuService instance;

    private MenuInteractions menu;
    private boolean running;
    private MenuService() {
        menu = new TextLineInterface();
        running = true;
    }

    public void run() {
        while (running) {
            menu.showMainMenu();
            int option = menu.getOption();
            switch (option) {
                case 1:
                    menu.showWerk();
                    break;
                case 2:
                    runKundenMenuLogic();
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

    private void runKundenMenuLogic() {
        menu.showKundenMenu();
        int option = menu.getOption();
        switch (option) {
            default:
                //TODO
                break;
        }
    }

    public static MenuService getInstance() {
        if(instance == null) instance = new MenuService();
        return instance;
    }

}
