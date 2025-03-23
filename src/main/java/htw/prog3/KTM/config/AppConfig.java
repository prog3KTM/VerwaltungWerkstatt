package htw.prog3.KTM.config;

import htw.prog3.KTM.controller.CarController;
import htw.prog3.KTM.controller.OrderController;
import htw.prog3.KTM.controller.ServiceJobController;
import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.database.TableHandler;
import htw.prog3.KTM.controller.CustomerController;
import htw.prog3.KTM.repository.CustomerRepository;
import htw.prog3.KTM.service.CustomerService;

import static org.jooq.impl.SQLDataType.VARCHAR;

public class AppConfig {

    private final CustomerController customerController;
    private final OrderController orderController;
    private final CarController carController;
    private final ServiceJobController serviceJobController;
    private final TableHandler tableHandler;
    private final DatabaseManager databaseManager;
    private boolean logenabled = false;

    public AppConfig() {
        databaseManager = new DatabaseManager();
        CustomerRepository customerRepository = new CustomerRepository(databaseManager);
        CustomerService customerService = new CustomerService(customerRepository);
        tableHandler = new TableHandler(databaseManager);
        //TableHandler example. TODO: Bitte eure Datentypen hier einfügen!!
//        Table table = new Table("Auto", new Column("id", VARCHAR(100)));
//        table.addColumn(new Column("model", VARCHAR(100)));
//        tableHandler.addTable(table);
        this.customerController = new CustomerController(customerService);
        this.carController = new CarController(databaseManager);
        this.serviceJobController = new ServiceJobController(databaseManager);
        this.orderController = new OrderController(databaseManager);
    }

    public OrderController getOrderController() {
        return orderController;
    }

    public TableHandler getTableHandler() {
        return tableHandler;
    }

    public CustomerController getCustomerController() {
        return customerController;
    }
    
    public CarController getCarController() {
        return carController;
    }
    
    public ServiceJobController getServiceJobController() {
        return serviceJobController;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public void setLogenabled(boolean logenabled) {
        this.logenabled = logenabled;
    }

    public boolean isLogenabled() {
        return logenabled;
    }
}
