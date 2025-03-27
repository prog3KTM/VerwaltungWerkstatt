package htw.prog3.KTM.model;

import htw.prog3.KTM.model.car.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicTest {
    
    @Test
    void testCarCreation() {
        // Create a car using the string status constructor
        Car car = new Car("1", "Test Model", "Test Brand", "ABC-123", "1");
        
        // Basic assertions
        assertEquals("1", car.getId());
        assertEquals("Test Model", car.getModel());
        assertEquals("Test Brand", car.getBrand());
        assertEquals("ABC-123", car.getLicensePlate());
        assertNotNull(car.getCarStatus());
    }
} 