package htw.prog3.KTM.controller;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.model.workshopinformation.WorkshopInformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkshopInformationControllerTest {

    private static WorkshopInformationController workshopInformationController;
    private static AppConfig appConfig;
    private static WorkshopInformation testWorkshopInfo;

    @BeforeAll
    static void setUp() {
        appConfig = new AppConfig();
        workshopInformationController = appConfig.getWorkshopInformationController();
        testWorkshopInfo = new WorkshopInformation(
                "Test Workshop",
                "Test Address 123",
                123456789,
                "test@workshop.com",
                "www.testworkshop.com",
                "DE123456789",
                "BUS123456",
                "DE12345678901234567890"
        );
    }
    
    @BeforeEach
    void cleanUp() {
        // Delete any existing information before test
        if (workshopInformationController.ifInformationExists()) {
            workshopInformationController.delete();
        }
    }

    @Test
    void save_ValidWorkshopInformation_InformationSavedSuccessfully() {
        // Act
        workshopInformationController.save(testWorkshopInfo);
        
        // Assert
        assertTrue(workshopInformationController.ifInformationExists());
    }

    @Test
    void getWerkstattInformation_AfterSaving_ReturnsCorrectInformation() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act
        WorkshopInformation retrievedInfo = workshopInformationController.getWerkstattInformation();
        
        // Assert
        assertNotNull(retrievedInfo);
        assertEquals("Test Workshop", retrievedInfo.name());
        assertEquals("Test Address 123", retrievedInfo.location());
        assertEquals(123456789, retrievedInfo.phone());
        assertEquals("test@workshop.com", retrievedInfo.email());
        assertEquals("www.testworkshop.com", retrievedInfo.website());
        assertEquals("DE123456789", retrievedInfo.vat());
        assertEquals("BUS123456", retrievedInfo.businessRegNumber());
        assertEquals("DE12345678901234567890", retrievedInfo.iban());
    }

    @Test
    void getName_AfterSaving_ReturnsCorrectName() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act
        String name = workshopInformationController.getName();
        
        // Assert
        assertEquals("Test Workshop", name);
    }

    @Test
    void getAddress_AfterSaving_ReturnsCorrectAddress() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act
        String address = workshopInformationController.getAddress();
        
        // Assert
        assertEquals("Test Address 123", address);
    }

    @Test
    void getPhoneNumber_AfterSaving_ReturnsCorrectPhoneNumber() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act
        int phoneNumber = workshopInformationController.getPhoneNumber();
        
        // Assert
        assertEquals(123456789, phoneNumber);
    }

    @Test
    void getEmail_AfterSaving_ReturnsCorrectEmail() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act
        String email = workshopInformationController.getEmail();
        
        // Assert
        assertEquals("test@workshop.com", email);
    }

    @Test
    void getWebsite_AfterSaving_ReturnsCorrectWebsite() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act
        String website = workshopInformationController.getWebsite();
        
        // Assert
        assertEquals("www.testworkshop.com", website);
    }

    @Test
    void getVAT_AfterSaving_ReturnsCorrectVAT() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act
        String vat = workshopInformationController.getVAT();
        
        // Assert
        assertEquals("DE123456789", vat);
    }

    @Test
    void getBusinessNumber_AfterSaving_ReturnsCorrectBusinessNumber() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act
        String businessNumber = workshopInformationController.getBusinessNumber();
        
        // Assert
        assertEquals("BUS123456", businessNumber);
    }

    @Test
    void getIban_AfterSaving_ReturnsCorrectIban() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act
        String iban = workshopInformationController.getIban();
        
        // Assert
        assertEquals("DE12345678901234567890", iban);
    }

    @Test
    void delete_ExistingInformation_InformationIsDeleted() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        assertTrue(workshopInformationController.ifInformationExists());
        
        // Act
        workshopInformationController.delete();
        
        // Assert
        assertFalse(workshopInformationController.ifInformationExists());
    }

    @Test
    void ifInformationExists_NoInformationSaved_ReturnsFalse() {
        // Act & Assert
        assertFalse(workshopInformationController.ifInformationExists());
    }

    @Test
    void ifInformationExists_AfterSaving_ReturnsTrue() {
        // Arrange
        workshopInformationController.save(testWorkshopInfo);
        
        // Act & Assert
        assertTrue(workshopInformationController.ifInformationExists());
    }
} 