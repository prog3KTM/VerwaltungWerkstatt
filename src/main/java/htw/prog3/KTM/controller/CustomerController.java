package htw.prog3.KTM.controller;

import htw.prog3.KTM.service.CustomerService;
import htw.prog3.KTM.model.customer.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerController {

    private final CustomerService customerService;

    // Dependency Injection through Constructor
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() throws SQLException {
        return customerService.getAllCustomer();
    }

    // Retrieve a customer by ID
    public Optional<Customer> getCustomerById(int id) throws SQLException {
        return customerService.getCustomerById(id);
    }

    // Create a new customer
    public void createCustomer(Customer customer) throws SQLException {
        customerService.createCustomer(customer);
    }

    // Update a customer
    public void updateCustomer(Customer customer) throws SQLException {
        customerService.updateCustomer(customer);
    }

    // Delete a customer
    public void deleteCustomer(int id) throws SQLException {
        customerService.deleteCustomer(id);
    }
}
