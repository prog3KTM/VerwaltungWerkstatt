package htw.prog3.KTM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import htw.prog3.KTM.model.customer.Customer;
import htw.prog3.KTM.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    // Konstruktor für Dependency Injection
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Alle Kunden abrufen
    public List<Customer> getAllCustomer() {
        logger.info("Abrufen aller Kunden");
        return customerRepository.findAll();
    }

    // Einen Kunden nach ID abrufen
    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    // Einen neuen Kunden speichern
    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    // Einen Kunden aktualisieren
    public void updateCustomer(Customer customer) {
        customerRepository.update(customer);
    }

    // Einen Kunden löschen
    public void deleteCustomer(int id) {
        customerRepository.delete(id);
    }
}

