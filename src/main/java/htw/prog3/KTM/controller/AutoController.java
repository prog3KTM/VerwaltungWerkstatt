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

    public Auto getAutoById(int id) {
        return autoRepository.findById(id).orElse(null); // Return null if no car is found
    }

    public void deleteAutoById(int id) {
        autoRepository.deleteById(id);
    }
}