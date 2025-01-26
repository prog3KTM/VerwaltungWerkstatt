package htw.prog3.KTM.controller;

import htw.prog3.KTM.model.Auto.Auto;
import htw.prog3.KTM.repository.AutoRepository;

import java.util.List;

public class AutoController {
    private final AutoRepository autoRepository;

    public AutoController() {
        this.autoRepository = new AutoRepository();
    }

    public List<Auto> getAllAutos() {
        return autoRepository.findAll();
    }

    public void addAuto(Auto auto) {
        autoRepository.save(auto);
    }

    public Auto getAutoById(int id) {
        return autoRepository.findById(id);
    }

    public void deleteAutoById(int id) {
        autoRepository.deleteById(id);
    }
}