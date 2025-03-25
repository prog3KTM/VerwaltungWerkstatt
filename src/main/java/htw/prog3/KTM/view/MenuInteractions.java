package htw.prog3.KTM.view;

import htw.prog3.KTM.model.customer.Customer;
import htw.prog3.KTM.model.order.Order;
import htw.prog3.KTM.model.order.OrderStatus;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformation;

import java.util.List;
import java.util.Optional;

public interface MenuInteractions {

    void showMainMenu();
    void showKunde(Customer customer);
    void showWerk(WorkshopInformation workshopInformation);
    void showKundenMenu();
    void showCarMenu();
    void showServiceMenu();
    void showOrderMenu();
    WorkshopInformation getWerkstattInformation();
    int getOption();
    void throwError(String err);

    void sendMessage(String msg);
    
    // New methods
    Customer getKundeInfo();
    String getCarInfo();
    String getServiceInfo();
    
    // Helper methods
    String getString(String prompt);
    int getInt(String prompt);

    //* Order functions
    void showOrder(Optional<Order> order);

    void showAllOrder(List<Order> orders);

    float getFloat(String prompt);

    OrderStatus getOrderStatus(String prompt);

    void hold();
}
