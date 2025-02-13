package htw.prog3.KTM.intecration;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.database.TableHandler;
import htw.prog3.KTM.model.werkstattInformation.WerkstattInformation;
import htw.prog3.KTM.repository.WerkstattInformationRepository;
import htw.prog3.KTM.service.WerkstattInformationService;
import htw.prog3.KTM.util.main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WerkstattInformationServiceTest {

    private static WerkstattInformationService werkstattInformationService;

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
        WerkstattInformationRepository werkstattInformationRepository = new WerkstattInformationRepository(databaseManager);
        werkstattInformationService = WerkstattInformationService.getInstance();
    }

    @Test
    void save() {
        werkstattInformationService.save(new WerkstattInformation(name, location, phone, email, website, vat, busregnum, iban));
    }

    @Test
    void getName() {
        assertEquals(name, werkstattInformationService.getName());
    }

    @Test
    void getEmail() {
        assertEquals(email, werkstattInformationService.getEmail());
    }

    @Test
    void getLocation() {
        assertEquals(location, werkstattInformationService.getLocation());
    }

    @Test
    void getPhone() {
        assertEquals(phone, werkstattInformationService.getPhone());
    }

    @Test
    void getWebsite() {
        assertEquals(website, werkstattInformationService.getWebsite());
    }

    @Test
    void getVat() {
        assertEquals(vat, werkstattInformationService.getVat());
    }

    @Test
    void getBusinessRegNumber() {
        assertEquals(busregnum, werkstattInformationService.getBusinessRegNumber());
    }

    @Test
    void getIban() {
        assertEquals(iban, werkstattInformationService.getIban());
    }

}