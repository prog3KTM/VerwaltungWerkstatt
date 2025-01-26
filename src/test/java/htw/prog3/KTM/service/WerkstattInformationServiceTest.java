package htw.prog3.KTM.service;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.WerkstattInformation.WerkstattInformation;
import htw.prog3.KTM.repository.KundeRepository;
import htw.prog3.KTM.repository.WerkstattInformationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WerkstattInformationServiceTest {

    private WerkstattInformationRepository werkstattInformationRepository;
    private WerkstattInformationService werkstattInformationService;

    @BeforeEach
    void setUp() {
        // Mock the dependencies
        werkstattInformationRepository = new WerkstattInformationRepository(new DatabaseManager());

        // Create an instance of KundeService with the mocked repository
        werkstattInformationService = new WerkstattInformationService(werkstattInformationRepository);
    }

    @Test
    void testSave() {
        WerkstattInformation werkstattInformation = new WerkstattInformation("Test", "Examplestr. 42", 4916322, "example@mustermann.com", "example.de", "IV12371841944", "BUS12893471234897", "DE4187964194981");
        werkstattInformationService.save(werkstattInformation);
    }

    @Test
    void getWerkstattInformationName() {
        String name = werkstattInformationService.getName();
        System.out.println(name);
    }
}