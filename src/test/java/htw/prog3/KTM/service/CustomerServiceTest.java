package htw.prog3.KTM.service;

import htw.prog3.KTM.model.customer.Customer;
import htw.prog3.KTM.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository);
        testCustomer = new Customer(1, "Test Customer", "Test Address", "123456789");
    }

    @Test
    void getAllCustomer_ReturnsAllCustomers() {
        // Arrange
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(testCustomer);
        expectedCustomers.add(new Customer(2, "Another Customer", "Another Address", "987654321"));
        
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        // Act
        List<Customer> actualCustomers = customerService.getAllCustomer();

        // Assert
        assertEquals(expectedCustomers.size(), actualCustomers.size());
        assertEquals(expectedCustomers, actualCustomers);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById_ExistingId_ReturnsCustomer() {
        // Arrange
        when(customerRepository.findById(1)).thenReturn(Optional.of(testCustomer));

        // Act
        Optional<Customer> result = customerService.getCustomerById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testCustomer, result.get());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    void getCustomerById_NonExistingId_ReturnsEmptyOptional() {
        // Arrange
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> result = customerService.getCustomerById(999);

        // Assert
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findById(999);
    }

    @Test
    void createCustomer_ValidCustomer_CallsRepositorySave() {
        // Act
        customerService.createCustomer(testCustomer);

        // Assert
        verify(customerRepository, times(1)).save(testCustomer);
    }

    @Test
    void updateCustomer_ValidCustomer_CallsRepositoryUpdate() {
        // Arrange
        Customer updatedCustomer = new Customer(1, "Updated Name", "Updated Address", "555555555");

        // Act
        customerService.updateCustomer(updatedCustomer);

        // Assert
        verify(customerRepository, times(1)).update(updatedCustomer);
    }

    @Test
    void deleteCustomer_ExistingId_CallsRepositoryDelete() {
        // Act
        customerService.deleteCustomer(1);

        // Assert
        verify(customerRepository, times(1)).delete(1);
    }
} 