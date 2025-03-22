package htw.prog3.KTM.view;

import htw.prog3.KTM.model.kunde.Customer;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformation;

public interface MenuInteractions {

    void showMainMenu();
    void showKunde(Customer customer);
    void showWerk();
    void showKundenMenu();
    void showAutoMenu();
    void showServiceMenu();
    WorkshopInformation getWerkstattInformation();
    int getOption();
    void throwError(String err);

    void sendMessage(String msg);
    
    // New methods
    Customer getKundeInfo();
    String getAutoInfo();
    String getServiceInfo();
    
    // Helper methods
    String getString(String prompt);
    int getInt(String prompt);
}
