package htw.prog3.KTM.unit;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.customer.Customer;
import htw.prog3.KTM.repository.CustomerRepository;
import htw.prog3.KTM.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private Logger logger;

    @BeforeEach
    void setUp() {
        // Mock the dependencies
        customerRepository = new CustomerRepository(new DatabaseManager());
        logger = LoggerFactory.getLogger(CustomerServiceTest.class);

        // Create an instance of KundeService with the mocked repository
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void testFindAll() {
        // Arrange: Set up the mock to return a list of customers
        Customer customer1 = new Customer(1, "Max Mustermann", "Straße 1", "0123456789");
        Customer customer2 = new Customer(2, "Erika Mustermann", "Straße 2", "0987654321");
        List<Customer> expectedKunden = Arrays.asList(customer1, customer2);


    }

    @Test
    void testFindById() {
        // Arrange: Set up the mock to return a customer for ID 1
        Customer customer = new Customer(1, "Max Mustermann", "Straße 1", "0123456789");

    }

    @Test
    void testSave() {
        // Arrange: Create a customer object to save
        Customer customer = new Customer(0, "Max Mustermann", "Straße 1", "0123456789");

        // Act: Call the service method to save the customer
        //kundeService.createKunde(kunde);


    }

    @Test

    void testDelete() {
        customerService.deleteCustomer(5);
        verify(customerRepository, times(1)).delete(5);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange: Set up the mock to return an empty Optional for a non-existent customer
        when(customerRepository.findById(42)).thenReturn(Optional.empty());

        // Act: Call the service method
        Optional<Customer> result = customerService.getCustomerById(42);

        // Assert: Verify that the result is empty
        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findById(42);
    }
}
