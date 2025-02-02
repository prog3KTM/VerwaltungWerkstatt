package htw.prog3.KTM.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import htw.prog3.KTM.model.kunde.Kunde;
import htw.prog3.KTM.repository.KundeRepository;

import java.util.List;
import java.util.Optional;

public class KundeService {

    private static final Logger logger = LoggerFactory.getLogger(KundeService.class);
    private final KundeRepository kundeRepository;

    // Konstruktor für Dependency Injection
    public KundeService(KundeRepository kundeRepository) {
        this.kundeRepository = kundeRepository;
    }

    // Alle Kunden abrufen
    public List<Kunde> getAllKunden() {
        logger.info("Abrufen aller Kunden");
        return kundeRepository.findAll();
    }

    // Einen Kunden nach ID abrufen
    public Optional<Kunde> getKundeById(int id) {
        return kundeRepository.findById(id);
    }

    // Einen neuen Kunden speichern
    public void createKunde(Kunde kunde) {
        kundeRepository.save(kunde);
    }

    // Einen Kunden aktualisieren
    public void updateKunde(Kunde kunde) {
        kundeRepository.update(kunde);
    }

    // Einen Kunden löschen
    public void deleteKunde(int id) {
        kundeRepository.delete(id);
    }
}

