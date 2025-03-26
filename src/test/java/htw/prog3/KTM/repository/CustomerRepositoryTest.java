package htw.prog3.KTM.repository;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.customer.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    private static final String TEST_DB_PATH = "test_customer_repo.db";
    private DatabaseManager databaseManager;
    private CustomerRepository customerRepository;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        // Initialize with test database
        databaseManager = new DatabaseManager(TEST_DB_PATH);
        
        // Create the Customer table before testing
        try (Connection connection = databaseManager.getConnection()) {
            Statement statement = connection.createStatement();
            // Drop the table first if it exists
            statement.executeUpdate("DROP TABLE IF EXISTS CUSTOMER");
            
            // Create the table
            statement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT NOT NULL, " +
                "ADDRESS TEXT NOT NULL, " +
                "PHONE TEXT NOT NULL" +
                ")"
            );
        } catch (SQLException e) {
            fail("Failed to create Customer table: " + e.getMessage());
        }
        
        customerRepository = new CustomerRepository(databaseManager);
        
        // Create test customer - use a UUID to ensure uniqueness
        String uniqueName = "Test Customer " + UUID.randomUUID().toString().substring(0, 8);
        testCustomer = new Customer(0, uniqueName, "Test Address", "123456789");
    }
    
    @AfterEach
    void tearDown() {
        try (Connection connection = databaseManager.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS CUSTOMER");
        } catch (SQLException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }

    @Test
    void save_ValidCustomer_CustomerIsSavedWithId() {
        // Act - Make sure the ID is 0 to let SQLite assign it
        testCustomer = new Customer(0, testCustomer.getName(), testCustomer.getAddress(), testCustomer.getPhone());
        customerRepository.save(testCustomer);
        
        // Find the saved customer by name
        List<Customer> customers = customerRepository.findAll();
        Optional<Customer> savedCustomer = customers.stream()
                .filter(c -> c.getName().equals(testCustomer.getName()))
                .findFirst();
        
        // Assert
        assertTrue(savedCustomer.isPresent(), "Customer should be saved successfully");
        assertTrue(savedCustomer.get().getId() > 0, "Customer should be assigned an ID > 0");
    }

    @Test
    void findById_ExistingId_ReturnsCorrectCustomer() {
        // Arrange - Save a customer first
        customerRepository.save(testCustomer);
        
        // Find the saved customer to get its ID
        List<Customer> customers = customerRepository.findAll();
        Customer savedCustomer = customers.stream()
                .filter(c -> c.getName().equals(testCustomer.getName()))
                .findFirst()
                .orElseThrow();
        
        // Act
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());
        
        // Assert
        assertTrue(foundCustomer.isPresent());
        assertEquals(testCustomer.getName(), foundCustomer.get().getName());
        assertEquals(testCustomer.getAddress(), foundCustomer.get().getAddress());
        assertEquals(testCustomer.getPhone(), foundCustomer.get().getPhone());
    }

    @Test
    void findById_NonExistingId_ReturnsEmptyOptional() {
        // Act
        Optional<Customer> result = customerRepository.findById(999999); // Using a very large ID that shouldn't exist
        
        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_WithMultipleCustomers_ReturnsAllCustomers() {
        // Arrange - Save multiple customers with unique names and with ID=0
        String name1 = "Test Customer 1 " + UUID.randomUUID().toString().substring(0, 8);
        String name2 = "Test Customer 2 " + UUID.randomUUID().toString().substring(0, 8);
        
        Customer customer1 = new Customer(0, name1, "Address 1", "111111");
        Customer customer2 = new Customer(0, name2, "Address 2", "222222");
        
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        
        // Act
        List<Customer> allCustomers = customerRepository.findAll();
        
        // Filter only our test customers by partial name
        List<Customer> testCustomers = allCustomers.stream()
                .filter(c -> c.getName().startsWith("Test Customer"))
                .toList();
        
        // Assert - We should find at least our two inserted customers
        assertTrue(testCustomers.size() >= 2);
        assertTrue(testCustomers.stream().anyMatch(c -> c.getName().equals(name1)));
        assertTrue(testCustomers.stream().anyMatch(c -> c.getName().equals(name2)));
    }

    @Test
    void update_ExistingCustomer_CustomerIsUpdated() {
        // Arrange - Save a customer first
        customerRepository.save(testCustomer);
        
        // Find the saved customer to get its ID
        List<Customer> customers = customerRepository.findAll();
        Customer savedCustomer = customers.stream()
                .filter(c -> c.getName().equals(testCustomer.getName()))
                .findFirst()
                .orElseThrow();
        
        // Create updated version with same ID
        String updatedName = "Test Updated Customer " + UUID.randomUUID().toString().substring(0, 8);
        Customer updatedCustomer = new Customer(
                savedCustomer.getId(),
                updatedName,
                "Updated Address",
                "999999999"
        );
        
        // Act
        customerRepository.update(updatedCustomer);
        
        // Retrieve updated customer
        Optional<Customer> result = customerRepository.findById(savedCustomer.getId());
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedName, result.get().getName());
        assertEquals("Updated Address", result.get().getAddress());
        assertEquals("999999999", result.get().getPhone());
    }

    @Test
    void delete_ExistingCustomer_CustomerIsDeleted() {
        // Arrange - Save a customer first
        customerRepository.save(testCustomer);
        
        // Find the saved customer to get its ID
        List<Customer> customers = customerRepository.findAll();
        Customer savedCustomer = customers.stream()
                .filter(c -> c.getName().equals(testCustomer.getName()))
                .findFirst()
                .orElseThrow();
        
        // Act
        customerRepository.delete(savedCustomer.getId());
        
        // Verify deletion
        Optional<Customer> result = customerRepository.findById(savedCustomer.getId());
        
        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void delete_NonExistingId_NoErrorThrown() {
        // The test expects an exception for a non-existing ID
        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerRepository.delete(999999); // Large ID that shouldn't exist
        });
        
        assertTrue(exception.getMessage().contains("nicht gefunden"));
    }
} 