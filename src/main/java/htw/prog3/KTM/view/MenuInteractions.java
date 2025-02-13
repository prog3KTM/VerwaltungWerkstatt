package htw.prog3.KTM.view;

import htw.prog3.KTM.model.kunde.Kunde;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;

public interface MenuInteractions {

    void showMainMenu();
    void showKunde(Kunde kunde);
    void showWerk();
    void showKundenMenu();
    WerkstattInformation getWerkstattInformation();
    int getOption();
    void throwError(String err);

}
