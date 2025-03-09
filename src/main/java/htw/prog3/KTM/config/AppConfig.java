package htw.prog3.KTM.config;

import htw.prog3.KTM.controller.AutoController;
import htw.prog3.KTM.controller.ServiceJobController;
import htw.prog3.KTM.database.Column;
import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.database.Table;
import htw.prog3.KTM.database.TableHandler;
import htw.prog3.KTM.repository.KundeRepository;
import htw.prog3.KTM.service.KundeService;
import htw.prog3.KTM.controller.KundeController;

import static org.jooq.impl.SQLDataType.VARCHAR;

public class AppConfig {

    private final KundeController kundeController;
    private final AutoController autoController;
    private final ServiceJobController serviceJobController;
    private final TableHandler tableHandler;
    private final DatabaseManager databaseManager;

    public AppConfig() {
        databaseManager = new DatabaseManager();
        KundeRepository kundeRepository = new KundeRepository(databaseManager);
        KundeService kundeService = new KundeService(kundeRepository);
        tableHandler = new TableHandler(databaseManager);
        //TableHandler example. TODO: Bitte eure Datentypen hier einfügen!!
//        Table table = new Table("Auto", new Column("id", VARCHAR(100)));
//        table.addColumn(new Column("model", VARCHAR(100)));
//        tableHandler.addTable(table);
        this.kundeController = new KundeController(kundeService);
        this.autoController = new AutoController(databaseManager);
        this.serviceJobController = new ServiceJobController(databaseManager);
    }

    public TableHandler getTableHandler() {
        return tableHandler;
    }

    public KundeController getKundeController() {
        return kundeController;
    }
    
    public AutoController getAutoController() {
        return autoController;
    }
    
    public ServiceJobController getServiceJobController() {
        return serviceJobController;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
