package htw.prog3.KTM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import htw.prog3.KTM.model.kunde.Customer;
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
    public List<Customer> getAllKunden() {
        logger.info("Abrufen aller Kunden");
        return customerRepository.findAll();
    }

    // Einen Kunden nach ID abrufen
    public Optional<Customer> getKundeById(int id) {
        return customerRepository.findById(id);
    }

    // Einen neuen Kunden speichern
    public void createKunde(Customer customer) {
        customerRepository.save(customer);
    }

    // Einen Kunden aktualisieren
    public void updateKunde(Customer customer) {
        customerRepository.update(customer);
    }

    // Einen Kunden löschen
    public void deleteKunde(int id) {
        customerRepository.delete(id);
    }
}

