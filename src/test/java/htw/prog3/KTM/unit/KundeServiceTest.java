package htw.prog3.KTM.unit;

import htw.prog3.KTM.model.Kunde;
import htw.prog3.KTM.repository.KundeRepository;
import htw.prog3.KTM.service.KundeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;



import static org.junit.jupiter.api.Assertions.*;

class KundeServiceTest {

    private KundeRepository kundeRepository;
    private KundeService kundeService;
    private Logger logger;

    @BeforeEach
    void setUp() {
        // Mock the dependencies

        logger = LoggerFactory.getLogger(KundeServiceTest.class);

        // Create an instance of KundeService with the mocked repository
        kundeService = new KundeService(kundeRepository);
    }

    @Test
    void testFindAll() {
        // Arrange: Set up the mock to return a list of customers
        Kunde kunde1 = new Kunde(1, "Max Mustermann", "Straße 1", "0123456789");
        Kunde kunde2 = new Kunde(2, "Erika Mustermann", "Straße 2", "0987654321");
        List<Kunde> expectedKunden = Arrays.asList(kunde1, kunde2);


    }

    @Test
    void testFindById() {
        // Arrange: Set up the mock to return a customer for ID 1
        Kunde kunde = new Kunde(1, "Max Mustermann", "Straße 1", "0123456789");

    }

    @Test
    void testSave() {
        // Arrange: Create a customer object to save
        Kunde kunde = new Kunde(0, "Max Mustermann", "Straße 1", "0123456789");

        // Act: Call the service method to save the customer
        kundeService.createKunde(kunde);


    }

    @Test
    void testDelete() {

    }

    @Test
    void testFindByIdNotFound() {
        // Arrange: Set up the mock to return an empty Optional for a non-existent customer

    }
}
