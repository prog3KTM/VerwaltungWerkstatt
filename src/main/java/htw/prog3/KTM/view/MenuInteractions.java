package htw.prog3.KTM.view;

import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;

public interface MenuInteractions {

    void showMainMenu();
    void showKunde();
    void showWerk();
    void showKundenMenu();
    WerkstattInformation getWerkstattInformation();

}
