package htw.prog3.KTM.controller;

import htw.prog3.KTM.service.KundeService;
import htw.prog3.KTM.model.Kunde.Kunde;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class KundeController {

    private final KundeService kundeService;

    // Dependency Injection durch Konstruktor
    public KundeController(KundeService kundeService) {
        this.kundeService = kundeService;
    }

    // Alle Kunden abrufen
    public List<Kunde> getAllKunden() throws SQLException {
        return kundeService.getAllKunden();
    }

    // Einen Kunden nach ID abrufen
    public Optional<Kunde> getKundeById(int id) throws SQLException {
        return kundeService.getKundeById(id);
    }

    // Einen neuen Kunden erstellen
    public void createKunde(Kunde kunde) throws SQLException {
        kundeService.createKunde(kunde);
    }

    // Einen Kunden aktualisieren
    public void updateKunde(Kunde kunde) throws SQLException {
        kundeService.updateKunde(kunde);
    }

    // Einen Kunden löschen
    public void deleteKunde(int id) throws SQLException {
        kundeService.deleteKunde(id);
    }
}
