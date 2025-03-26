package htw.prog3.KTM.repository;

import htw.prog3.KTM.model.customer.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCustomerTest {
    
    @Test
    void testCustomerCreation() {
        // Create a simple customer object
        Customer customer = new Customer(1, "Test Customer", "Test Address", "123456789");
        
        // Basic assertions
        assertEquals(1, customer.getId());
        assertEquals("Test Customer", customer.getName());
        assertEquals("Test Address", customer.getAddress());
        assertEquals("123456789", customer.getPhone());
    }
} 