package htw.prog3.KTM.service;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.WerkstattInformation.WerkstattInformation;
import htw.prog3.KTM.repository.WerkstattInformationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WerkstattInformationServiceTest {

    private WerkstattInformationService werkstattInformationService;

    //- Testdata
    private final String name = "WerkstattInformationServiceTest";
    private final String email = "werkstatt@gmail.com";
    private final String location = "location";
    private final int phone = 49123812;
    private final String website = "website";
    private final String vat = "vat";
    private final String busregnum = "busregnum";
    private final String iban = "iban";

    @BeforeEach
    void setUp() {
        WerkstattInformationRepository werkstattInformationRepository = new WerkstattInformationRepository(new DatabaseManager());
        werkstattInformationService = new WerkstattInformationService(werkstattInformationRepository);
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

    @Test
    void save() {
        werkstattInformationService.save(new WerkstattInformation(name, location, phone, email, website, vat, busregnum, iban));
    }


}