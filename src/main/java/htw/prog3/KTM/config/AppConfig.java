package htw.prog3.KTM.config;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.repository.KundeRepository;
import htw.prog3.KTM.service.KundeService;
import htw.prog3.KTM.controller.KundeController;

public class AppConfig {

    private final KundeController kundeController;

    public AppConfig() {
        DatabaseManager databaseManager = new DatabaseManager();
        KundeRepository kundeRepository = new KundeRepository(databaseManager);
        KundeService kundeService = new KundeService(kundeRepository);
        this.kundeController = new KundeController(kundeService);
    }

    public KundeController getKundeController() {
        return kundeController;
    }
}
