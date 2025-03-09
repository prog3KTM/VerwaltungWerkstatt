package htw.prog3.KTM.view;

import htw.prog3.KTM.model.kunde.Kunde;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;

public interface MenuInteractions {

    void showMainMenu();
    void showKunde(Kunde kunde);
    void showWerk();
    void showKundenMenu();
    void showAutoMenu();
    void showServiceMenu();
    WerkstattInformation getWerkstattInformation();
    int getOption();
    void throwError(String err);

    void sendMessage(String msg);
    
    // New methods
    Kunde getKundeInfo();
    String getAutoInfo();
    String getServiceInfo();
    
    // Helper methods
    String getString(String prompt);
    int getInt(String prompt);
}
