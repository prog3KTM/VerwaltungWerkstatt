package htw.prog3.KTM.model.car;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    
    @Test
    void testCarConstruction() {
        // Create a car with string status
        Car car = new Car(1, "Test Model", "Test Brand", "TEST-123", "AVAILABLE");
        
        // Basic assertions
        assertEquals(1, car.getId());
        assertEquals("Test Model", car.getModel());
        assertEquals("Test Brand", car.getBrand());
        assertEquals("TEST-123", car.getLicensePlate());
        assertNotNull(car.getCarStatus());
    }
    
    @Test
    void testCarSetters() {
        // Create a car
        Car car = new Car(1, "Test Model", "Test Brand", "TEST-123", "AVAILABLE");
        
        // Update values
        car.setId(2);
        car.setModel("Updated Model");
        car.setBrand("Updated Brand");
        car.setLicensePlate("TEST-456");
        
        // Verify updates
        assertEquals(2, car.getId());
        assertEquals("Updated Model", car.getModel());
        assertEquals("Updated Brand", car.getBrand());
        assertEquals("TEST-456", car.getLicensePlate());
    }
} 