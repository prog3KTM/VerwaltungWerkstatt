package htw.prog3.KTM.controller;

import htw.prog3.KTM.database.DatabaseManager;
import htw.prog3.KTM.model.auto.Auto;
import htw.prog3.KTM.repository.AutoRepository;

import java.util.List;

public class AutoController {
    private final AutoRepository autoRepository;

    public AutoController(DatabaseManager databaseManager) {
        this.autoRepository = new AutoRepository(databaseManager);
    }

    public List<Auto> getAllAutos() {
        return autoRepository.findAll();
    }

    public void addAuto(Auto auto) {
        autoRepository.save(auto);
    }

    // Change method parameter to String
    public Auto getAutoById(String id) {
        return autoRepository.findById(id).orElse(null); // Returns null if no car is found
    }

    // Change method parameter to String
    public void deleteAutoById(String id) {
        autoRepository.deleteById(id);
    }
}