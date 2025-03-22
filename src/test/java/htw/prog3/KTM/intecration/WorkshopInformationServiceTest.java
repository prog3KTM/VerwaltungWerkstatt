package htw.prog3.KTM.intecration;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.database.TableHandler;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformation;
import htw.prog3.KTM.repository.WorkshopInformationRepository;
import htw.prog3.KTM.service.WorkshopinformationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorkshopInformationServiceTest {

    private static WorkshopinformationService workshopinformationService;

    //- Testdata
    private final String name = "WerkstattInformationServiceTest";
    private final String email = "werkstatt@gmail.com";
    private final String location = "location";
    private final int phone = 49123812;
    private final String website = "website";
    private final String vat = "vat";
    private final String busregnum = "busregnum";
    private final String iban = "iban";

    @BeforeAll
    static void setUp() {
        DatabaseManager databaseManager = new DatabaseManager("test.db");
        AppConfig appConfig = new AppConfig();
        TableHandler tableHandler = appConfig.getTableHandler();
        tableHandler.dropTables();
        tableHandler.checkTables();
        WorkshopInformationRepository workshopInformationRepository = new WorkshopInformationRepository(databaseManager);
        workshopinformationService = new WorkshopinformationService(databaseManager);
    }

    @Test
    void save() {
        workshopinformationService.save(new WorkshopInformation(name, location, phone, email, website, vat, busregnum, iban));
    }

    @Test
    void getName() {
        assertEquals(name, workshopinformationService.getName());
    }

    @Test
    void getEmail() {
        assertEquals(email, workshopinformationService.getEmail());
    }

    @Test
    void getLocation() {
        assertEquals(location, workshopinformationService.getLocation());
    }

    @Test
    void getPhone() {
        assertEquals(phone, workshopinformationService.getPhone());
    }

    @Test
    void getWebsite() {
        assertEquals(website, workshopinformationService.getWebsite());
    }

    @Test
    void getVat() {
        assertEquals(vat, workshopinformationService.getVat());
    }

    @Test
    void getBusinessRegNumber() {
        assertEquals(busregnum, workshopinformationService.getBusinessRegNumber());
    }

    @Test
    void getIban() {
        assertEquals(iban, workshopinformationService.getIban());
    }

}