package htw.prog3.KTM.controller;

import htw.prog3.KTM.config.AppConfig;
import htw.prog3.KTM.model.customer.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CustomerControllerTest {

    private static CustomerController customerController;
    private static AppConfig appConfig;

    @BeforeAll
    static void setUp() {
        appConfig = new AppConfig();
        customerController = appConfig.getCustomerController();
    }
    
    @BeforeEach
    void clearData() throws SQLException {
        // Delete existing test customers
        List<Customer> customers = customerController.getAllCustomers();
        for (Customer customer : customers) {
            if (customer.getName().startsWith("Test")) {
                customerController.deleteCustomer(customer.getId());
            }
        }
    }

    private static Customer customer_testdata = new Customer(0, "Test Customer", "Test Address", "123456789");

    @Test
    void createCustomer_ValidCustomer_CustomerCreatedSuccessfully() throws SQLException {
        Customer customer = new Customer(0, "Test New Customer", "New Address", "987654321");
        customerController.createCustomer(customer);
        
        // Find the customer by querying all customers and filtering
        List<Customer> customers = customerController.getAllCustomers();
        Optional<Customer> created = customers.stream()
                .filter(c -> c.getName().equals("Test New Customer"))
                .findFirst();
        
        assertEquals("Test New Customer", created.get().getName());
        
        // Clean up
        customerController.deleteCustomer(created.get().getId());
    }

    @Test
    void getCustomerById_ExistingCustomerId_ReturnsCorrectCustomer() throws SQLException {
        // Create a test customer first
        customerController.createCustomer(customer_testdata);
        
        // Find the customer by querying all customers and filtering
        List<Customer> customers = customerController.getAllCustomers();
        Customer created = customers.stream()
                .filter(c -> c.getName().equals("Test Customer"))
                .findFirst()
                .orElseThrow();
        
        Optional<Customer> customer = customerController.getCustomerById(created.getId());
        assertEquals("Test Customer", customer.get().getName());
        
        // Clean up
        customerController.deleteCustomer(created.getId());
    }

    @Test
    void getAllCustomers_AfterAddingCustomer_ReturnsListIncludingNewCustomer() throws SQLException {
        // Get initial count
        int initialCount = customerController.getAllCustomers().size();
        
        // Add a customer
        customerController.createCustomer(customer_testdata);
        
        // Get updated list
        List<Customer> customers = customerController.getAllCustomers();
        assertEquals(initialCount + 1, customers.size());
        
        // Clean up
        Optional<Customer> created = customers.stream()
                .filter(c -> c.getName().equals("Test Customer"))
                .findFirst();
        if (created.isPresent()) {
            customerController.deleteCustomer(created.get().getId());
        }
    }

    @Test
    void updateCustomer_WithNewDetails_CustomerIsUpdatedSuccessfully() throws SQLException {
        // Create a test customer first
        customerController.createCustomer(customer_testdata);
        
        // Find the customer by querying all customers and filtering
        List<Customer> customers = customerController.getAllCustomers();
        Customer created = customers.stream()
                .filter(c -> c.getName().equals("Test Customer"))
                .findFirst()
                .orElseThrow();
        
        // Update the customer
        Customer updatedCustomer = new Customer(created.getId(), "Test Updated Customer", "Updated Address", "555555555");
        customerController.updateCustomer(updatedCustomer);
        
        // Get the updated customer
        Optional<Customer> customer = customerController.getCustomerById(created.getId());
        assertEquals("Test Updated Customer", customer.get().getName());
        assertEquals("Updated Address", customer.get().getAddress());
        assertEquals("555555555", customer.get().getPhone());
        
        // Clean up
        customerController.deleteCustomer(created.getId());
    }

    @Test
    void deleteCustomer_ExistingCustomerId_CustomerIsDeleted() throws SQLException {
        // Create a test customer first
        customerController.createCustomer(customer_testdata);
        
        // Find the customer by querying all customers and filtering
        List<Customer> customers = customerController.getAllCustomers();
        Customer created = customers.stream()
                .filter(c -> c.getName().equals("Test Customer"))
                .findFirst()
                .orElseThrow();
        
        // Delete the customer
        customerController.deleteCustomer(created.getId());
        
        // Verify deletion
        Optional<Customer> customer = customerController.getCustomerById(created.getId());
        assertFalse(customer.isPresent());
    }
} 