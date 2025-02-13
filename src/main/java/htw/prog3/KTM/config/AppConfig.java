package htw.prog3.KTM.config;

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
    }

    public TableHandler getTableHandler() {
        return tableHandler;
    }

    public KundeController getKundeController() {
        return kundeController;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
